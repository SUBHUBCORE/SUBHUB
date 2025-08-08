package io.subHub.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CreateUuidUtil {

    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(StringPool.DASH, StringPool.EMPTY);
    }
}
