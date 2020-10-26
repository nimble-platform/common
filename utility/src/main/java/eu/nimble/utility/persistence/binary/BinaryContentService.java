package eu.nimble.utility.persistence.binary;

import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.CommonSpringBridge;
import eu.nimble.utility.exception.BinaryContentException;
import eu.nimble.utility.exception.NimbleExceptionMessageCode;
import eu.nimble.utility.persistence.JPARepositoryFactory;
import eu.nimble.utility.persistence.TransactionAcrossMultipleDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Service to manage data in the binary content database.
 *
 * Created by suat on 03-Dec-18.
 */
public class BinaryContentService implements TransactionAcrossMultipleDB{
    private static final String TABLE_NAME = "binary_content";
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_MIME_CODE = "mime_code";
    private static final String COLUMN_NAME_FILE_NAME = "file_name";
    private static final String COLUMN_NAME_VALUE = "value_";
    private static final String QUERY_INSERT_CONTENT = "INSERT INTO  " + TABLE_NAME + "( " + COLUMN_NAME_ID + "," + COLUMN_NAME_MIME_CODE + "," + COLUMN_NAME_FILE_NAME + "," + COLUMN_NAME_VALUE + ") VALUES (?,?,?,?)";
    private static final String QUERY_SELECT_CONTENT_BY_URI = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " = ?";
    private static final String QUERY_SELECT_CONTENT_BY_URIS = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " IN (%s)"; // query to complete in the relevant methods
    private static final String QUERY_DELETE_CONTENT_BY_URIS = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + " IN (%s)"; // query to complete in the relevant method
    private static final String QUERY_GET_URIS_HAVING_REFERENCES = "SELECT binaryObject.uri FROM BinaryObjectType binaryObject WHERE binaryObject.uri in :uris group by binaryObject.uri having count(*) > 0";
    private int batchSize = 1000;
    private static Logger logger = LoggerFactory.getLogger(BinaryContentService.class);

    // this connection is used when all operations should be performed in a single transaction
    private Connection singleTransactionConnection = null;

    @Override
    public void beginTransaction() {
        try {
            // get connection
            singleTransactionConnection = CommonSpringBridge.getInstance().getBinarycontentdbDataSource().getConnection();
            // start the transaction
            singleTransactionConnection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Failed to get connection while setting single transaction connection",e);
        }
    }

    @Override
    public void commit() {
        if(singleTransactionConnection != null){
            try {
                // commit the changes
                if(!singleTransactionConnection.isClosed()){
                    singleTransactionConnection.commit();
                }
            } catch (SQLException e) {
                logger.error("Failed to commit single transaction connection",e);
            }
            finally {
                try {
                    // close the connection
                    if (!singleTransactionConnection.isClosed()) {
                        singleTransactionConnection.close();
                    }
                }
                catch (SQLException e){
                    logger.error("Failed to close single transaction connection",e);
                }
            }
        }
    }

    @Override
    public void rollback() {
        if(singleTransactionConnection != null){
            try {
                // rollback the updates
                if(!singleTransactionConnection.isClosed()){
                    singleTransactionConnection.rollback();
                }

            } catch (SQLException e) {
                logger.error("Failed to rollback single transaction connection",e);
            }
            finally {
                try {
                    // close the transaction
                    if (!singleTransactionConnection.isClosed()) {
                        singleTransactionConnection.close();
                    }
                }
                catch (SQLException e){
                    logger.error("Failed to close single transaction connection",e);
                }
            }
        }
    }

    /**
     * Extracts the binary content from the given binary objects and stores them in the binary content db.
     *
     * Also, nullifies the given binary object unless the binary content is an image, otherwise puts a thumbnail instead of the original content.
     * @param binaryObjects
     */
    public void persistBinaryObjects(List<BinaryObjectType> binaryObjects) {
        List<String> brokenImages = new ArrayList<>();
        List<BinaryObjectType> binaryObjectsToBePersisted = new ArrayList<>();

        for (BinaryObjectType binaryObject : binaryObjects) {
            if (binaryObject.getUri() == null || binaryObject.getUri().equals("")) {

                String mimeCode = binaryObject.getMimeCode();

                // create BinaryObjectType object
                BinaryObjectType binaryContentDbObject = new BinaryObjectType();
                binaryContentDbObject.setMimeCode(mimeCode);
                binaryContentDbObject.setFileName(binaryObject.getFileName());

                // check whether the binary object is an image or not
                if (mimeCode.contains("image/")) {
                    try {
                        // scale the image
                        byte[] bytes = scaleImage(binaryObject.getValue(), false, mimeCode);
                        // if bytes is equal to null,that means that we do not have a valid image
                        if (bytes == null) {
                            // since this is a broken image, add its name to the list
                            brokenImages.add(binaryObject.getFileName());
                        }
                        binaryContentDbObject.setValue(bytes);
                        binaryObject.setValue(scaleImage(binaryObject.getValue(), true, mimeCode));

                    } catch (IOException e) {
                        brokenImages.add(binaryObject.getFileName());
                    }

                } else {
                    binaryContentDbObject.setValue(binaryObject.getValue());
                    binaryObject.setValue(null);
                }

                // retrieve a uri for Binary Object
                String uri = new BinaryContentService().getURI();
                // set uris
                binaryObject.setUri(uri);
                binaryContentDbObject.setUri(uri);
                // add binary object to the list
                binaryObjectsToBePersisted.add(binaryContentDbObject);
            }
        }

        if (brokenImages.size() > 0) {
            throw new BinaryContentException(NimbleExceptionMessageCode.INVALID_IMAGE.toString(), Collections.singletonList(String.join(",", brokenImages)));
        }

        // save binary objects
        createContents(binaryObjectsToBePersisted);
    }

    private byte[] scaleImage(byte[] value, boolean isThumbnail, String mimeCode) throws IOException {
        // get correct format of the image (after image/... part)
        String format = mimeCode.substring(6);

        ByteArrayInputStream bais = new ByteArrayInputStream(value);
        BufferedImage image = null;
        try {
            image = new ImageScaler().scale(bais, isThumbnail);
        } finally{
            bais.close();
        }
        // if image is equal to null,that means that we do not have a valid image
        if(image == null){
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, format, baos);
        }finally {
            baos.close();
        }

        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }

    public BinaryObjectType createContent(BinaryObjectType binaryObjectType){
        return createContent(binaryObjectType,true);
    }

    public BinaryObjectType createContent(BinaryObjectType binaryObjectType, Boolean checkUri) {
        Connection c = null;
        PreparedStatement ps = null;

        if(checkUri && (binaryObjectType.getUri() == null || !binaryObjectType.getUri().startsWith(CommonSpringBridge.getInstance().getBinaryContentUrl()))){
            binaryObjectType.setUri(getURI());
        }

        try {
            c = singleTransactionConnection != null ? singleTransactionConnection: CommonSpringBridge.getInstance().getBinarycontentdbDataSource().getConnection();
            ps = c.prepareStatement(QUERY_INSERT_CONTENT);
            ps.setString(1, binaryObjectType.getUri());
            ps.setString(2, binaryObjectType.getMimeCode());
            ps.setString(3, binaryObjectType.getFileName());
            ps.setBytes(4, binaryObjectType.getValue());
            int queryResult = ps.executeUpdate();
            ps.close();

            if (queryResult > 0) {
                logger.info("Binary content created with uri: {}", binaryObjectType.getUri());
            } else {
                logger.warn("Failed to created binary content for uri: {}, file name: {}, mime type: {}", binaryObjectType.getUri(), binaryObjectType.getFileName(), binaryObjectType.getMimeCode());
            }

            return binaryObjectType;

        } catch (SQLException e) {
            String msg = String.format("Failed to retrieve binary content for uri: %s", binaryObjectType.getUri());
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, null, String.format("While getting binary content for uri: %s", binaryObjectType.getUri()));
        }
    }

    // we assume that given binary objects have valid uris
    public void createContents(List<BinaryObjectType> binaryObjectTypes) {
        Connection c = null;
        PreparedStatement ps = null;

        if(binaryObjectTypes == null || binaryObjectTypes.size() == 0){
            return;
        }

        // get uris
        StringBuilder uris = new StringBuilder();
        int size = binaryObjectTypes.size();
        for(int i = 0 ; i < size; i++){
            uris.append(binaryObjectTypes.get(i).getUri());
            if(i != size-1){
                uris.append(",");
            }
        }

        try {
            c = singleTransactionConnection != null ? singleTransactionConnection: CommonSpringBridge.getInstance().getBinarycontentdbDataSource().getConnection();
            ps = c.prepareStatement(QUERY_INSERT_CONTENT);

            int i = 0;
            for(BinaryObjectType binaryObjectType:binaryObjectTypes){
                ps.setString(1, binaryObjectType.getUri());
                ps.setString(2, binaryObjectType.getMimeCode());
                ps.setString(3, binaryObjectType.getFileName());
                ps.setBytes(4, binaryObjectType.getValue());
                ps.addBatch();
                i++;

                if(i % batchSize == 0 || i == binaryObjectTypes.size()){
                    ps.executeBatch();
                }
            }

            ps.close();

            logger.info("Binary contents created with uris: {}", uris.toString());
        } catch (SQLException e) {
            String msg = String.format("Failed to create binary contents for uris: %s", uris.toString());
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, null, String.format("While creating binary contents for uris: %s", uris.toString()));
        }
    }

    public BinaryObjectType retrieveContent(String uri) {
        List<BinaryObjectType> results = retrieveContents(Arrays.asList(uri));
        if(results.size() > 0) {
            return results.get(0);
        } else {
            return null;
        }
    }

    public String getURI(){
        return CommonSpringBridge.getInstance().getBinaryContentUrl() + ":" + UUID.randomUUID().toString();
    }

    public List<BinaryObjectType> retrieveContents(List<String> uris) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // construct the condition
        String condition = "";
        for(int i=0; i<uris.size(); i++) {
            if(i == uris.size()-1){
                condition += "'"+uris.get(i)+"'";
            }else {
                condition += "'"+uris.get(i)+"'"+",";
            }
        }

        try {
            c = singleTransactionConnection != null ? singleTransactionConnection: CommonSpringBridge.getInstance().getBinarycontentdbDataSource().getConnection();
            ps = c.prepareStatement(String.format(QUERY_SELECT_CONTENT_BY_URIS, condition));
            rs = ps.executeQuery();
            List<BinaryObjectType> binaryObjects = new ArrayList<>();
            while (rs.next()) {
                BinaryObjectType result = new BinaryObjectType();
                result.setMimeCode(rs.getString(COLUMN_NAME_MIME_CODE));
                result.setValue(rs.getBytes(COLUMN_NAME_VALUE));
                result.setUri(rs.getString(COLUMN_NAME_ID));
                result.setFileName(rs.getString(COLUMN_NAME_FILE_NAME));
                binaryObjects.add(result);
            }
            rs.close();
            ps.close();

            return binaryObjects;

        } catch (SQLException e) {
            String msg = String.format("Failed to retrieve binary contents for uris: %s", uris.toString());
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, rs, String.format("While getting binary contents for uris: %s", uris.toString()));
        }
    }

    public void deleteContentIdentity(String uri) {
        deleteContentsIdentity(Arrays.asList(uri));
    }

    public void deleteContentsIdentity(List<String> uris) {
        Connection c = null;
        PreparedStatement ps = null;

        // construct the condition
        String condition = "";
        for(int i=0; i<uris.size(); i++) {
            if(i == uris.size()-1){
                condition += "'"+uris.get(i)+"'";
            }else {
                condition += "'"+uris.get(i)+"'"+",";
            }
        }

        try {
            c = CommonSpringBridge.getInstance().getBinarycontentdbDataSource().getConnection();
            ps = c.prepareStatement(String.format(QUERY_DELETE_CONTENT_BY_URIS, condition));
            //            ps.setString(1, condition.substring(0, condition.length() - 1));
            int queryResult = ps.executeUpdate();

            if (queryResult > 0) {
                logger.info("Binary content deleted for uris: {}, stacktrace: {}", uris, Arrays.toString(Thread.currentThread().getStackTrace()).replaceAll(",", "\n"));
            } else {
                logger.warn("Failed to delete binary content for uris: {}, stacktrace: {}", uris, Arrays.toString(Thread.currentThread().getStackTrace()).replaceAll(",", "\n"));
            }

            ps.close();

        } catch (SQLException e) {
            String msg = String.format("Failed to delete binary content for uris: %s", uris);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, null, String.format("While deleting binary content for uris: %s", uris));
        }
    }

    public void deleteContent(String uri) {
        deleteContents(Arrays.asList(uri));
    }

    // delete the ones which have no BinaryObject references and discard the rest
    public void deleteContents(List<String> uris) {
        Connection c = null;
        PreparedStatement ps = null;

        // get the uris having references
        List<String> urisHavingReferences = new JPARepositoryFactory().forCatalogueRepository().getEntities(QUERY_GET_URIS_HAVING_REFERENCES,new String[]{"uris"}, new Object[]{uris});
        // discard those uris and delete the rest (having no BinaryObject reference)
        uris.removeAll(urisHavingReferences);

        // return if there is nothing to delete
        if(uris.size() == 0){
            return;
        }

        // construct the condition
        String condition = "";
        for(int i=0; i<uris.size(); i++) {
            if(i == uris.size()-1){
                condition += "'"+uris.get(i)+"'";
            }else {
                condition += "'"+uris.get(i)+"'"+",";
            }
        }

        try {
            c = singleTransactionConnection != null ? singleTransactionConnection: CommonSpringBridge.getInstance().getBinarycontentdbDataSource().getConnection();
            ps = c.prepareStatement(String.format(QUERY_DELETE_CONTENT_BY_URIS, condition));
//            ps.setString(1, condition.substring(0, condition.length() - 1));
            int queryResult = ps.executeUpdate();

            if (queryResult > 0) {
                logger.info("Binary content deleted for uris: {}, stacktrace: {}", uris, Arrays.toString(Thread.currentThread().getStackTrace()).replaceAll(",", "\n"));
            } else {
                logger.warn("Failed to delete binary content for uris: {}, stacktrace: {}", uris, Arrays.toString(Thread.currentThread().getStackTrace()).replaceAll(",", "\n"));
            }

            ps.close();

        } catch (SQLException e) {
            String msg = String.format("Failed to delete binary content for uris: %s", uris);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);

        } finally {
            closeResources(c, ps, null, String.format("While deleting binary content for uris: %s", uris));
        }
    }

    private void closeResources(Connection c, Statement ps, ResultSet rs, String msg) {
        // we should not close the connection if single transaction connection is enabled
        if (c != null && singleTransactionConnection == null) {
            try {
                if (!c.isClosed()) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close connection: {}", msg, e);
            }
        }
        if (ps != null) {
            try {
                if (!ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close prepared statement: {}", msg, e);
            }
        }
        if (rs != null) {
            try {
                if (!rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                logger.warn("Failed to close result set: {}", msg, e);
            }
        }
    }
}
