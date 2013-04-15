package au.csiro.iskclient.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.stereotype.Service;

import au.csiro.iskclient.services.responses.DbImageResult;

@Service
public class IskClientService {
    private XmlRpcClient client;
    private final Log log = LogFactory.getLog(getClass());

    public IskClientService() throws MalformedURLException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        //config.setServerURL(new URL("http://127.0.0.1:31128/RPC"));
        config.setServerURL(new URL("http://115.146.86.158/RPC"));
        //config.setEnabledForExtensions(true);

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
    public List<DbImageResult> queryImgID(int databaseId, int id, int numres, boolean fast) throws ServiceException {
        List<DbImageResult> parsedImages = new ArrayList<DbImageResult>();

        //Make request
        Object[] params = new Object[] {databaseId, id, numres, fast};
        Object[] response = null;
        try {
            response = (Object[]) client.execute("queryImgID", params);
        } catch (XmlRpcException e) {
            throw new ServiceException("Error executing queryImgId", e);
        }

        //Parse response
        for (Object res : response) {
            Object[] r = (Object[]) res;
            int rid = (Integer) r[0];
            parsedImages.add(new DbImageResult(databaseId, rid, (Double) r[1]));
        }

        return parsedImages;
    }
}
