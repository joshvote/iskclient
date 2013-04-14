package au.csiro.iskclient.services;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Service for maintaining the mapping between ISK image IDs and the underlying image urls
 * @author Joshua
 *
 */
@Service
public class IskImageDatabaseService {
    private final Log log = LogFactory.getLog(getClass());

    private Map<Integer, String> imageIds;
    private Random r;

    public IskImageDatabaseService() {
        imageIds = new HashMap<Integer, String>();
        r = new Random();

        InputStream is = null;
        String[] line = null;
        try {
            is = getClass().getResourceAsStream("imageid.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            while ((line = reader.readNext()) != null) {
                if (!line[0].isEmpty()) {
                    imageIds.put(Integer.parseInt(line[0]), line[1]);
                }
            }
        } catch (Exception ex) {
            log.error(String.format("Unable to open/parse resource imageid.csv for line %1$s. Image ID to URL mapping will not function.",Arrays.toString(line)), ex);
        } finally {
            IOUtils.closeQuietly(is);
        }

        log.info("Total image ids parsed:" + imageIds.size());
    }

    public String getUrlForImageId(int id) {
        return imageIds.get(id);
    }

    /**
     * Gets n Random image ids of images referenced by this service. Duplicates are possible
     * @param count
     * @return
     */
    public synchronized int[] getRandomImages(int count) {
        Set<Integer> keys = imageIds.keySet();
        Integer[] keysArray = keys.toArray(new Integer[keys.size()]);
        int[] result = new int[count];

        for (int i = 0; i < result.length; i++) {
            int index = r.nextInt(keysArray.length);
            result[i] = keysArray[index];
        }

        return result;
    }
}
