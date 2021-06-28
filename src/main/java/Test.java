import vbpo.st.collector.common.dto.CollectorConfiguration;
import vbpo.st.collector.common.dto.InvoiceDownloadResult;
import vbpo.st.collector.common.dto.InvoiceDownloadResultInfo;
import vbpo.st.collector.common.enumration.ResultStatus;
import vbpo.st.collector.common.service.CollectorApiService;
import vbpo.st.collector.common.service.CollectorTokenRequester;
import vbpo.st.collector.common.service.LogService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {

        CollectorConfiguration collectorConfiguration = new CollectorConfiguration();
        collectorConfiguration.setTokenUrl("http://10.8.0.11:9999/oauth/token");
        collectorConfiguration.setClientId("web_app");
        collectorConfiguration.setClientSecret("changeit");
        collectorConfiguration.setUsername("super_admin");
        collectorConfiguration.setPassword("admin");
        collectorConfiguration.setGrantType("password");
        collectorConfiguration.setRestEndPoint("http://10.8.0.11:8082/api");

        CollectorApiService collectorApiService = new CollectorApiService(collectorConfiguration);
        CollectorTokenRequester.requestNewAccessToken(collectorConfiguration);
        System.out.println(CollectorTokenRequester.getCollectorToken().getAccessToken());

        InvoiceDownloadResult invoiceDownloadResult = new InvoiceDownloadResult();

        InvoiceDownloadResultInfo invoiceDownloadResultInfo = new InvoiceDownloadResultInfo();
        invoiceDownloadResultInfo.setCollectJobId(2L);
        invoiceDownloadResultInfo.setResultStatus(ResultStatus.ERROR);
        invoiceDownloadResultInfo.setSavedFolder("/data/test");

        LogService logService = new LogService();
        logService.logInfo("Test 1");
        logService.logDebug("Test 2");
        logService.logWarn("Test 3");
        logService.logError("Test 4");

        invoiceDownloadResultInfo.setLogs(logService.getLogs());

        List<File> files = new ArrayList<>();
        files.add(new File("/data/test1/1.pdf"));
        files.add(new File("/data/test1/2.xml"));

        invoiceDownloadResult.setInvoiceDownloadResultInfo(invoiceDownloadResultInfo);
        invoiceDownloadResult.setFiles(files);

        System.out.println(collectorApiService.completeDownload(invoiceDownloadResult));

    }

}
