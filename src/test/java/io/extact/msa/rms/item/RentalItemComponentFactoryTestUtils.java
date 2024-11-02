package io.extact.msa.rms.item;

import java.io.IOException;
import java.nio.file.Path;

import io.extact.msa.rms.platform.fw.persistence.file.io.FileAccessor;
import io.extact.msa.rms.platform.fw.persistence.file.io.PathResolver;
import io.extact.msa.spring.item.application.RentalItemService;
import io.extact.msa.spring.item.persistence.file.RentalItemArrayConverter;
import io.extact.msa.spring.item.persistence.file.RentalItemFileRepository;

/**
 * テストケースで利用するコンポーネントファクトリユーティルクラス。
 */
public class RentalItemComponentFactoryTestUtils {

    private static final String RENTAL_ITEM_TEST_FILE_NAME = "rentalItemTest.csv";

    public static RentalItemFileRepository newRentalItemFileRepository(PathResolver pathResolver) throws IOException {
        Path tempFile =  FileAccessor.copyResourceToRealPath(RENTAL_ITEM_TEST_FILE_NAME, pathResolver);
        FileAccessor fa = new FileAccessor(tempFile);
        return new RentalItemFileRepository(fa, RentalItemArrayConverter.INSTANCE);
    }

    public static RentalItemService newRentalItemService(PathResolver pathResolver) throws IOException {
        return new RentalItemService(newRentalItemFileRepository(pathResolver));
    }
}
