package au.csiro.iskclient.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.csiro.iskclient.services.responses.DbImageResult;

@Service
public class IskClientService {
    private XmlRpcClient client;

    @Autowired
    public IskClientService() throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://127.0.0.1:31128/RPC"));

        client = new XmlRpcClient();
        client.setConfig(config);
    }

    /**
     * Return the most similar images to the supplied one. The supplied image must be already indexed, and is referenced by its ID.
     *
     * @param id Target image id.
     * @param numres Number of results to return. The target image is on the result list.
     * @param fast  if true, only the average color for each image is considered. Image geometry/features are ignored. Search is faster this way.
     * @see http://www.imgseek.net/isk-daemon/documents-1/api-reference#queryImgID
     * @return
     */
    public List<DbImageResult> queryImgID(long id, int numres, boolean fast) {
        client.execute("queryImgID", pParams)
    }
}
