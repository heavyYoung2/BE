package hongik.heavyYoung.domain.locker.service.general.strategy;

import hongik.heavyYoung.domain.application.entity.Application;
import hongik.heavyYoung.domain.member.entity.Member;

public interface LockerApplicationStrategy {
    boolean supports(Application lockerApplication);
    void apply(Member member,Application lockerApplication);
}
