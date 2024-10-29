package io.extact.msa.rms.item.domain.model;

import io.extact.msa.spring.platform.fw.domain.constraint.RmsId;
import io.extact.msa.spring.platform.fw.domain.model.Identity;

public record ItemId(
        @RmsId int id) implements Identity {
}
