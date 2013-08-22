/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.ilabs.library.batchservicebroker;

import uq.ilabs.library.datatypes.batch.ClientSubmissionReport;
import uq.ilabs.library.datatypes.batch.ExperimentStatus;
import uq.ilabs.library.datatypes.batch.LabExperimentStatus;
import uq.ilabs.library.datatypes.batch.LabStatus;
import uq.ilabs.library.datatypes.batch.ResultReport;
import uq.ilabs.library.datatypes.batch.StatusCodes;
import uq.ilabs.library.datatypes.batch.ValidationReport;
import uq.ilabs.library.datatypes.batch.WaitEstimate;

/**
 *
 * @author uqlpayne
 */
public class ConvertTypes {

    /**
     *
     * @param arrayOfString
     * @return String[]
     */
    public static String[] Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.ArrayOfString arrayOfString) {
        String[] strings = null;

        if (arrayOfString != null) {
            strings = arrayOfString.getString().toArray(new String[0]);
        }

        return strings;
    }

    /**
     *
     * @param proxyClientSubmissionReport
     * @return ClientSubmissionReport
     */
    public static ClientSubmissionReport Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.ClientSubmissionReport proxyClientSubmissionReport) {
        ClientSubmissionReport clientSubmissionReport = null;

        if (proxyClientSubmissionReport != null) {
            clientSubmissionReport = new ClientSubmissionReport();
            clientSubmissionReport.setExperimentId(proxyClientSubmissionReport.getExperimentID());
            clientSubmissionReport.setMinTimeToLive(proxyClientSubmissionReport.getMinTimeToLive());
            clientSubmissionReport.setValidationReport(Convert(proxyClientSubmissionReport.getVReport()));
            clientSubmissionReport.setWaitEstimate(Convert(proxyClientSubmissionReport.getWait()));
        }

        return clientSubmissionReport;
    }

    /**
     *
     * @param proxyExperimentStatus
     * @return ExperimentStatus
     */
    public static ExperimentStatus Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.ExperimentStatus proxyExperimentStatus) {
        ExperimentStatus experimentStatus = null;

        if (proxyExperimentStatus != null) {
            experimentStatus = new ExperimentStatus();
            experimentStatus.setEstRemainingRuntime(proxyExperimentStatus.getEstRemainingRuntime());
            experimentStatus.setEstRuntime(proxyExperimentStatus.getEstRuntime());
            experimentStatus.setStatusCode(StatusCodes.ToStatusCode(proxyExperimentStatus.getStatusCode()));
            experimentStatus.setWaitEstimate(Convert(proxyExperimentStatus.getWait()));
        }

        return experimentStatus;
    }

    /**
     *
     * @param proxyLabExperimentStatus
     * @return LabExperimentStatus
     */
    public static LabExperimentStatus Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.LabExperimentStatus proxyLabExperimentStatus) {
        LabExperimentStatus labExperimentStatus = null;

        if (proxyLabExperimentStatus != null) {
            labExperimentStatus = new LabExperimentStatus();
            labExperimentStatus.setMinTimetoLive(proxyLabExperimentStatus.getMinTimetoLive());
            labExperimentStatus.setExperimentStatus(Convert(proxyLabExperimentStatus.getStatusReport()));
        }

        return labExperimentStatus;
    }

    /**
     *
     * @param proxyLabStatus
     * @return LabStatus
     */
    public static LabStatus Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.LabStatus proxyLabStatus) {
        LabStatus labStatus = null;

        if (proxyLabStatus != null) {
            labStatus = new LabStatus();
            labStatus.setOnline(proxyLabStatus.isOnline());
            labStatus.setLabStatusMessage(proxyLabStatus.getLabStatusMessage());
        }

        return labStatus;
    }

    /**
     *
     * @param proxyResultReport
     * @return ResultReport
     */
    public static ResultReport Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.ResultReport proxyResultReport) {
        ResultReport resultReport = null;

        if (proxyResultReport != null) {
            resultReport = new ResultReport();
            resultReport.setErrorMessage(proxyResultReport.getErrorMessage());
            resultReport.setXmlExperimentResults(proxyResultReport.getExperimentResults());
            resultReport.setStatusCode(StatusCodes.ToStatusCode(proxyResultReport.getStatusCode()));
            resultReport.setXmlBlobExtension(proxyResultReport.getXmlBlobExtension());
            resultReport.setXmlResultExtension(proxyResultReport.getXmlResultExtension());
            resultReport.setWarningMessages(Convert(proxyResultReport.getWarningMessages()));
        }

        return resultReport;
    }

    /**
     *
     * @param proxyValidationReport
     * @return ValidationReport
     */
    public static ValidationReport Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.ValidationReport proxyValidationReport) {
        ValidationReport validationReport = null;

        if (proxyValidationReport != null) {
            validationReport = new ValidationReport();
            validationReport.setAccepted(proxyValidationReport.isAccepted());
            validationReport.setErrorMessage(proxyValidationReport.getErrorMessage());
            validationReport.setEstRuntime(proxyValidationReport.getEstRuntime());
            validationReport.setWarningMessages(Convert(proxyValidationReport.getWarningMessages()));
        }

        return validationReport;
    }

    /**
     *
     * @param proxyWaitEstimate
     * @return WaitEstimate
     */
    public static WaitEstimate Convert(edu.mit.ilab.ilabs.batchservicebroker.proxy.WaitEstimate proxyWaitEstimate) {
        WaitEstimate waitEstimate = null;

        if (proxyWaitEstimate != null) {
            waitEstimate = new WaitEstimate();
            waitEstimate.setEffectiveQueueLength(proxyWaitEstimate.getEffectiveQueueLength());
            waitEstimate.setEstWait(proxyWaitEstimate.getEstWait());
        }

        return waitEstimate;
    }
}
