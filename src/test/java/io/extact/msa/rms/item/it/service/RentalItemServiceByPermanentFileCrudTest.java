package io.extact.msa.rms.item.it.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.platform.fw.persistence.file.FileUtils;
import io.extact.msa.rms.test.junit5.JulToSLF4DelegateExtension;
import io.extact.msa.rms.test.utils.ClearOpenTelemetryContextCdiExtension;
import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.AddExtension;
import io.helidon.microprofile.tests.junit5.HelidonTest;

/**
 * GitHubActionの実行環境でwindowsを選択すると環境的な問題でディレクトリ作成に
 * 権限なしでエラーになるため実行抑止する場合はRMS_CI_ENVの変数を設定する。
 * (設定例)
 * <pre>
 *   env:
 *    RMS_CI_ENV: github
 * </pre>
 */
@DisabledIfEnvironmentVariable(named = "RMS_CI_ENV", matches = "github")
@HelidonTest
@AddExtension(ClearOpenTelemetryContextCdiExtension.class)
@AddConfig(key = "rms.persistence.apiType", value = "file")
@AddConfig(key = "rms.persistence.csv.type", value = "permanent")
@AddConfig(key = "rms.persistence.csv.permanent.directory", value = RentalItemServiceByPermanentFileCrudTest.TEST_PERMANENT_DIR)
@ExtendWith(JulToSLF4DelegateExtension.class)
class RentalItemServiceByPermanentFileCrudTest extends AbstractRentalItemServiceCrudTest {

    static final String TEST_PERMANENT_DIR = "./target/temp-integrationtest";

    @AfterAll
    static void teardownAfterAll() throws Exception {
        FileUtils.deleteDirectoryUnderFiles(TEST_PERMANENT_DIR);
    }
}
