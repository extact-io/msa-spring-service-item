package io.extact.msa.spring.item.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.method.MethodValidationException;

import io.extact.msa.spring.item.domain.RentalItemRepository;
import io.extact.msa.spring.item.domain.model.ItemId;
import io.extact.msa.spring.item.domain.model.RentalItem;
import io.extact.msa.spring.platform.fw.exception.RmsPersistenceException;

/**
 * RentalItemリポジトリのFileとJPA実装に共通なテストクラス。
 * 下記観点でテストを行っている。
 * ・入力モデルと出力モデルの項目マッピング
 * ・エラーパスの確認
 * ・モデルに対するvalidationはモデルの単体テストで網羅されているためここではパスの確認のみ行っている
 * <p>
 * {@linkplain Transactional} と {@linkplain Rollback}が付いている理由は{@linkplain} AbstractPersonRepositoryTest}を参照
 */
@Transactional
@Rollback
public abstract class AbstractRentalItemRepositoryTest {

    private static final RentalItem item1 = RentalItem.reconstruct(1, "A0001", "レンタル品1号");
    private static final RentalItem item2 = RentalItem.reconstruct(2, "A0002", "レンタル品2号");
    private static final RentalItem item3 = RentalItem.reconstruct(3, "A0003", "レンタル品3号");
    private static final RentalItem item4 = RentalItem.reconstruct(4, "A0004", "レンタル品4号");

    protected abstract RentalItemRepository repository();

    @Test
    void testGet() {

        // given
        ItemId itemId = new ItemId(1);
        // when
        Optional<RentalItem> actual = repository().find(itemId);
        // then
        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(item1);

        // given
        ItemId notFoundId = new ItemId(999);
        // when
        actual = repository().find(notFoundId);
        // then
        assertThat(actual).isNotPresent();
    }

    @Test
    void testGetAll() {
        // given
        List<RentalItem> expected = List.of(item1, item2, item3, item4);
        // when
        List<RentalItem> actual = repository().findAll();
        // then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void testUpdate() {
        // given
        RentalItem updateItem = RentalItem.reconstruct(4, "UPDATE", "UPDATE");
        // when
        repository().update(updateItem);
        //then
        assertThat(repository().find(updateItem.getId()).get()).isEqualTo(updateItem);
    }

    @Test
    void testUpdateOnValidationError() {
        // given
        RentalItem validateErrorItem = RentalItem.reconstruct(4, "", "UPDATE"); // serialNoがブランク
        // when & then
        assertThrows(MethodValidationException.class, () -> {
            repository().update(validateErrorItem);
        });
    }

    @Test
    void testUpdateOnDuplicate() {
        // given
        RentalItem duplicateItem = RentalItem.reconstruct(4, "A0001", "UPDATE"); // A0001は既に登録済み
        // when & then
        assertThatCode(() -> repository().update(duplicateItem))
                // 重複チェックは上位で行うので正常に処理できることを確認
                .doesNotThrowAnyException();
    }

    @Test
    void testUpdateOnNotFound() {
        // given
        RentalItem notFoundItem = RentalItem.reconstruct(999, "UPDATE", "UPDATE");
        // when & then
        RmsPersistenceException exception = assertThrows(RmsPersistenceException.class, () -> {
            repository().update(notFoundItem);
        });
        assertThat(exception).hasMessageContaining("id:" + notFoundItem.getId().id());
    }

    @Test
    void testAdd() {
        // given
        RentalItem addItem = RentalItem.reconstruct(5, "ADD", "ADD");
        // when
        repository().add(addItem);
        // then
        assertThat(repository().find(addItem.getId()).get()).isEqualTo(addItem);
    }

    @Test
    void testAddOnValidationError() {
        // given
        RentalItem validateErrorItem = RentalItem.reconstruct(6, "", "ADD"); // serialNoがブランク
        // when & then
        assertThrows(MethodValidationException.class, () -> {
            repository().add(validateErrorItem);
        });
    }

    @Test
    void testAddOnDuplicate() {
        // given
        RentalItem duplicateItem = RentalItem.reconstruct(7, "A0001", "ADD"); // A0001は既に登録済み
        // when & then
        assertThatCode(() -> repository().add(duplicateItem))
                // 重複チェックは上位で行うので正常に処理できることを確認
                .doesNotThrowAnyException();
    }

    @Test
    void testDelete() {
        // given
        RentalItem deleteItem = RentalItem.reconstruct(1, "delete", "delete");
        // when
        repository().delete(deleteItem);
        // then
        assertThat(repository().find(deleteItem.getId())).isEmpty();
    }

    @Test
    void testDeleteOnValidationError() {
        // given
        RentalItem validateErrorItem = RentalItem.reconstruct(2, "", "ADD"); // serialNoがブランク
        // when & then
        assertThrows(MethodValidationException.class, () -> {
            repository().delete(validateErrorItem);
        });
    }

    @Test
    void testDeleteOnNotFound() {
        // given
        RentalItem notFoundItem = RentalItem.reconstruct(999, "DELETE", "DELETE");
        // when & then
        RmsPersistenceException exception = assertThrows(RmsPersistenceException.class, () -> {
            repository().delete(notFoundItem);
        });
        assertThat(exception).hasMessageContaining("id:" + notFoundItem.getId().id());
    }
}
