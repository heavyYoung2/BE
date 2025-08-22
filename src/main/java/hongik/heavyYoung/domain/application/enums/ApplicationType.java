package hongik.heavyYoung.domain.application.enums;

import java.util.EnumSet;

public enum ApplicationType {
    LOCKER_MAIN, // 사물함 정규 신청
    LOCKER_ADDITIONAL, // 사물함 추가 신청
    SNACK; // 간식 행사 신청

    public static final EnumSet<ApplicationType> LOCKER = EnumSet.of(LOCKER_MAIN, LOCKER_ADDITIONAL);
    }
