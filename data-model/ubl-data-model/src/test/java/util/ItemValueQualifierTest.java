package util;

import eu.nimble.service.model.ubl.extension.ItemPropertyValueQualifier;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by suat on 23-Jan-19.
 */
public class ItemValueQualifierTest {
    @Test
    public void testValueOfAlternative()  {
        ItemPropertyValueQualifier e = ItemPropertyValueQualifier.valueOfAlternative("Binary");
        Assert.assertEquals(ItemPropertyValueQualifier.FILE, e);
    }
}
