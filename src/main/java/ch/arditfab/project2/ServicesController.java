package ch.arditfab.project2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

import ai.djl.modality.cv.ImageFactory;
import ai.djl.ModelException;
import ai.djl.modality.cv.Image;
import ai.djl.translate.TranslateException;

import java.io.IOException;

@RestController
public class ServicesController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }

    @GetMapping("/ping")
    public String ping() {
        return "StyleTransfer app is up and running!";
    }

    @PostMapping(value="/styletransfer", consumes = "multipart/form-data")
    public String predict(@RequestParam("file") MultipartFile file, @RequestParam("artist") String artist) throws IOException, ModelException, TranslateException {
        Image image = ImageFactory.getInstance().fromInputStream(file.getInputStream());
        String originalFilename = file.getOriginalFilename();
        String filenameWithoutExtension = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
        Image output = StyleTransfer.transformImage(image, artist);
        String base64 = StyleTransfer.save(output, artist.toString(), "src/main/resources/static/build/output/", filenameWithoutExtension);
        return base64;
    }

}