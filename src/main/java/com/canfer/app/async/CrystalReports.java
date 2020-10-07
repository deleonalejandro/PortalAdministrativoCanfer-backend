package com.canfer.app.async;

import org.springframework.stereotype.Service;
import com.crystaldecisions.reports.sdk.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class CrystalReports {

	public InputStream exportPdf(String author) throws Exception {
        ReportClientDocument reportClientDoc = new ReportClientDocument();
        reportClientDoc.open("product.rpt", OpenReportOptions._openAsReadOnly);

        reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "Author By", author);
        return reportClientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
    }
}
