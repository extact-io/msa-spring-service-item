package io.extact.msa.rms.item.persistence.file;

import static io.extact.msa.rms.item.RentalItemComponentFactoryTestUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.item.domain.model.RentalItem;
import io.extact.msa.rms.item.persistence.AbstractRentalItemRepositoryTest;
import io.extact.msa.rms.item.persistence.RentalItemRepository;
import io.extact.msa.rms.platform.fw.persistence.file.io.PathResolver;
import io.extact.msa.rms.platform.test.PathResolverParameterExtension;
import io.extact.msa.rms.platform.test.PlatformTestUtils;

@ExtendWith(PathResolverParameterExtension.class)
class RentalItemFileRepositoryTest extends AbstractRentalItemRepositoryTest {

    private RentalItemFileRepository repository;

    @BeforeEach
    void setUp(PathResolver pathResolver) throws Exception {
        repository = newRentalItemFileRepository(pathResolver);
    }

    @Test
    void testAdd() throws Exception {

        RentalItem addRentalItem = RentalItem.ofTransient("A0005", "レンタル品5号");

        repository.add(addRentalItem);

        List<String[]> records = PlatformTestUtils.getAllRecords(repository.getStoragePath());
        String[] lastRecord = (String[]) records.get(records.size() - 1);
        String[] expectRow = { "-1", "A0005", "レンタル品5号" };
        expectRow[0] = String.valueOf(records.size());

        assertThat(lastRecord).containsExactly(expectRow);
    }

    @Override
    protected RentalItemRepository repository() {
        return repository;
    }
}
