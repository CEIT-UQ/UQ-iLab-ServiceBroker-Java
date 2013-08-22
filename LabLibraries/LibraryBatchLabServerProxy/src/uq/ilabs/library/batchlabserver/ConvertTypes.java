/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.ilabs.library.batchlabserver;

import uq.ilabs.library.datatypes.batch.ExperimentStatus;
import uq.ilabs.library.datatypes.batch.LabExperimentStatus;
import uq.ilabs.library.datatypes.batch.LabStatus;
import uq.ilabs.library.datatypes.batch.ResultReport;
import uq.ilabs.library.datatypes.batch.StatusCodes;
import uq.ilabs.library.datatypes.batch.SubmissionReport;
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
    public static String[] Convert(edu.mit.ilab.batchlabserver.proxy.ArrayOfString arrayOfString) {
        String[] strings = null;

        if (arrayOfString != null) {
            strings = arrayOfString.getString().toArray(new String[0]);
        }

        return strings;
    }

    /**
     *
     * @param proxyExperimentStatus
     * @return ExperimentStatus
     */
    public static ExperimentStatus Convert(edu.mit.ilab.batchlabserver.proxy.ExperimentStatus proxyExperimentStatus) {
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
    public static LabExperimentStatus Convert(edu.mit.ilab.batchlabserver.proxy.LabExperimentStatus proxyLabExperimentStatus) {
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
    public static LabStatus Convert(edu.mit.ilab.batchlabserver.proxy.LabStatus proxyLabStatus) {
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
    public static ResultReport Convert(edu.mit.ilab.batchlabserver.proxy.ResultReport proxyResultReport) {
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
     * @param proxySubmissionReport
     * @return SubmissionReport
     */
    public static SubmissionReport Convert(edu.mit.ilab.batchlabserver.proxy.SubmissionReport proxySubmissionReport) {
        SubmissionReport submissionReport = null;

        if (proxySubmissionReport != null) {
            submissionReport = new SubmissionReport();
            submissionReport.setExperimentId(proxySubmissionReport.getExperimentID());
            submissionReport.setMinTimeToLive(proxySubmissionReport.getMinTimetoLive());
            submissionReport.setValidationReport(Convert(proxySubmissionReport.getVReport()));
            submissionReport.setWaitEstimate(Convert(proxySubmissionReport.getWait()));
        }

        return submissionReport;
    }

    /**
     *
     * @param proxyValidationReport
     * @return ValidationReport
     */
    public static ValidationReport Convert(edu.mit.ilab.batchlabserver.proxy.ValidationReport proxyValidationReport) {
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
    public static WaitEstimate Convert(edu.mit.ilab.batchlabserver.proxy.WaitEstimate proxyWaitEstimate) {
        WaitEstimate waitEstimate = null;

        if (proxyWaitEstimate != null) {
            waitEstimate = new WaitEstimate();
            waitEstimate.setEffectiveQueueLength(proxyWaitEstimate.getEffectiveQueueLength());
            waitEstimate.setEstWait(proxyWaitEstimate.getEstWait());
        }

        return waitEstimate;
    }
}
