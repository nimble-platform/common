package eu.nimble.utility.bp;

import eu.nimble.utility.exception.NimbleException;
import eu.nimble.utility.exception.NimbleExceptionMessageCode;

import java.util.List;
import java.util.stream.Collectors;

public class BusinessWorkflowUtil {

    /**
     * Validates the given business workflow
     * Throws a {{@link NimbleException}} if the given workflow is invalid. The exception contains the error message.
     */
    public static void validateBusinessWorkflow(List<String> workflow) throws NimbleException {
        // get valid process ids
        List<String> validProcessIds = ClassProcessTypeMap.getBusinessProcessIds();
        // check whether the given list contains any invalid process ids
        List<String> invalidProcessIds = workflow.stream().filter(processId -> !validProcessIds.contains(processId)).collect(Collectors.toList());
        if (invalidProcessIds.size() > 0) {
            throw new NimbleException(NimbleExceptionMessageCode.INVALID_PROCESS_IDS.toString(), invalidProcessIds);
        }
        // check whether any mandatory business processes are missing
        if (workflow.contains(ClassProcessTypeMap.CAMUNDA_PROCESS_ID_ORDER) && !workflow.contains(ClassProcessTypeMap.CAMUNDA_PROCESS_ID_NEGOTIATION)) {
            throw new NimbleException(NimbleExceptionMessageCode.BAD_REQUEST_MISSING_NEGOTIATION_PROCESS_FOR_ORDER.toString());
        }
        if (workflow.contains(ClassProcessTypeMap.CAMUNDA_PROCESS_ID_TRANSPORT_EXECUTION_PLAN) && !workflow.contains(ClassProcessTypeMap.CAMUNDA_PROCESS_ID_NEGOTIATION)) {
            throw new NimbleException(NimbleExceptionMessageCode.BAD_REQUEST_MISSING_NEGOTIATION_PROCESS_FOR_TRANSPORT_EXECUTION_PLAN.toString());
        }
        if (workflow.contains(ClassProcessTypeMap.CAMUNDA_PROCESS_ID_FULFILMENT) && (!workflow.contains(ClassProcessTypeMap.CAMUNDA_PROCESS_ID_NEGOTIATION) || !workflow.contains(ClassProcessTypeMap.CAMUNDA_PROCESS_ID_ORDER))) {
            throw new NimbleException(NimbleExceptionMessageCode.BAD_REQUEST_MISSING_NEGOTIATION_ORDER_PROCESS_FOR_FULFILMENT.toString());
        }
    }

}
