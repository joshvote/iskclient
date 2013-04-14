package au.csiro.iskclient.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.csiro.iskclient.services.IskClientService;
import au.csiro.iskclient.services.IskImageDatabaseService;
import au.csiro.iskclient.services.ServiceException;
import au.csiro.iskclient.services.responses.DbImageResult;
import au.csiro.iskclient.view.Image;
import au.csiro.iskclient.view.JSONView;

@Controller
public class BrowseController {

    private final Log log = LogFactory.getLog(getClass());

    String remoteImageLibraryPattern;
    IskClientService clientService;
    IskImageDatabaseService imgDbService;

    @Autowired
    public BrowseController(IskClientService clientService,
            IskImageDatabaseService imgDbService) {
        this.clientService = clientService;
        this.imgDbService = imgDbService;
        this.remoteImageLibraryPattern = "http://115.146.86.158/img/%1$s"; //This is a hacky way of pointing to a place where our images can be looked up...
    }

    /**
     * Utility method generating a 'standard' response model.
     *
     * @param success The result of the operation
     * @param data [Optional] Raw response information. Can be null, must be serialisable into a JSON object.
     * @param message [Optional] A string indicating more information about status of information
     * @param debugInfo [Optional] Debugging Information. Can be null, must be serialisable into a JSON object
     * @param matchedResults [Optional] The number of results available (not necessarily the count of data)
     * @return
     */
    private ModelMap generateResponseModel(boolean success, Object data, String message) {
        ModelMap model = new ModelMap();

        model.put("data", data);
        model.put("success", success);
        model.put("msg", message);

        return model;
    }

    /**
     * Utility method to generate a standard ModelAndView response for rendering JSON
     * @param success The result of the operation
     * @param data [Optional] Raw response information. Can be null, must be serialisable into a JSON object.
     * @param message [Optional] A string indicating more information about status of information
     * @return
     */
    private ModelAndView generateJsonResponse(boolean success, Object data, String message) {
        JSONView view = new JSONView();
        ModelMap model = generateResponseModel(success, data, message);

        return new ModelAndView(view, model);
    }

    private String imageIdToUrl(int imageId) {
        String relativeUrl = this.imgDbService.getUrlForImageId(imageId);
        return String.format(this.remoteImageLibraryPattern, relativeUrl);
    }

    @RequestMapping("getRandomImages.do")
    public ModelAndView getRandomImages(@RequestParam(value="count", required=false, defaultValue="25")Integer count) {


        int[] imgIds = imgDbService.getRandomImages(count);
        List<Image> images = new ArrayList<Image>();

        for (int i = 0; i < imgIds.length; i++) {
            images.add(new Image(imgIds[i], imageIdToUrl(imgIds[i]), 0.0));
        }

        return generateJsonResponse(true, images, "");
    }

    @RequestMapping("getSimilarImages.do")
    public ModelAndView getSimilarImages(@RequestParam(value="imageId", required=false) Integer imageId,
            @RequestParam(value="count", required=false, defaultValue="25") Integer count) {


        if (imageId == null) {
            return getRandomImages(count);
        }

        List<DbImageResult> response = null;
        List<Image> parsedImages = new ArrayList<Image>();
        try {
            response = clientService.queryImgID(imageId, count, false);
        } catch (ServiceException e) {
            log.error("Problem querying for similar images", e);
            return generateJsonResponse(false, null, e.getMessage());
        }

        for (DbImageResult dbImg : response) {
            parsedImages.add(new Image(dbImg.getId(), imageIdToUrl(dbImg.getId()), dbImg.getScore()));
        }

        return generateJsonResponse(true, parsedImages, "");
    }
}
