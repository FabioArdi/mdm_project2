package ch.arditfab.project2;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.translator.StyleTransferTranslatorFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class StyleTransfer {
    private static final Logger logger = LoggerFactory.getLogger(StyleTransfer.class);

    private StyleTransfer() {} 

    public static Image transformImage(Image image, String artist)
            throws IOException, ModelException, TranslateException {
                Image output = transfer(image, artist);
        return output;
    }
    
    public static Image transfer(Image image, String artist)
    throws IOException, ModelNotFoundException, MalformedModelException,
            TranslateException { 
        System.out.println("artist: " + artist);
        String modelName = "style_" + artist.toString().toLowerCase() + ".zip";
        System.out.println("modelName: " + modelName);
        String modelUrl =
                "https://mlrepo.djl.ai/model/cv/image_generation/ai/djl/pytorch/cyclegan/0.0.1/"
                        + modelName;
        System.out.println("modelUrl: " + modelUrl);

        Criteria<Image, Image> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.IMAGE_GENERATION)
                        .setTypes(Image.class, Image.class)
                        .optModelUrls(modelUrl)
                        .optProgress(new ProgressBar())
                        .optTranslatorFactory(new StyleTransferTranslatorFactory())
                        .optEngine("PyTorch")
                        .build();

        try (ZooModel<Image, Image> model = criteria.loadModel();
                Predictor<Image, Image> styler = model.newPredictor()) {
            return styler.predict(image);
        }
    }

    public static String save(Image image, String name, String path, String filename) throws IOException {
        Path outputPath = Paths.get(path);
        Files.createDirectories(outputPath);
        Path imagePath = outputPath.resolve(filename + "_" + name + ".png");
        image.save(Files.newOutputStream(imagePath), "png");
        return "/build/output/" + filename + "_" + name + ".png";
    }
}

