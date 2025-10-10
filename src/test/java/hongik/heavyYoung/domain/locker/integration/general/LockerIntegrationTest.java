package hongik.heavyYoung.domain.locker.integration.general;

import hongik.heavyYoung.domain.locker.entity.Locker;
import hongik.heavyYoung.domain.locker.entity.LockerAssignment;
import hongik.heavyYoung.domain.locker.enums.LockerRentalStatus;
import hongik.heavyYoung.domain.locker.enums.LockerStatus;
import hongik.heavyYoung.domain.locker.repository.LockerAssignmentRepository;
import hongik.heavyYoung.domain.locker.repository.LockerRepository;
import hongik.heavyYoung.domain.member.repository.MemberRepository;
import hongik.heavyYoung.domain.member.entity.Member;
import hongik.heavyYoung.domain.member.enums.MemberStatus;
import hongik.heavyYoung.domain.member.enums.StudentFeeStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(statements = {
        "ALTER TABLE member AUTO_INCREMENT = 1",
        "ALTER TABLE locker AUTO_INCREMENT = 1",
        "ALTER TABLE locker_assignment AUTO_INCREMENT = 1",
        "ALTER TABLE member_application AUTO_INCREMENT = 1",
        "ALTER TABLE application AUTO_INCREMENT = 1"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class LockerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LockerAssignmentRepository lockerAssignmentRepository;

    @Autowired
    private RedisCacheManager cacheManager;

    @BeforeEach
    void setUp(){
        Locker locker1 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(1)
                .lockerStatus(LockerStatus.IN_USE)
                .build();

        Locker locker2 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(2)
                .lockerStatus(LockerStatus.IN_USE)
                .build();

        Locker locker3 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(3)
                .lockerStatus(LockerStatus.BROKEN)
                .build();

        Locker locker4 = Locker.builder()
                .lockerSection("A")
                .lockerNumber(4)
                .lockerStatus(LockerStatus.AVAILABLE)
                .build();

        Locker locker5 = Locker.builder()
                .lockerSection("B")
                .lockerNumber(1)
                .lockerStatus(LockerStatus.AVAILABLE)
                .build();

        lockerRepository.save(locker1);
        lockerRepository.save(locker2);
        lockerRepository.save(locker3);
        lockerRepository.save(locker4);
        lockerRepository.save(locker5);

        Member member1 = Member.builder()
                .studentId("C011117")
                .studentName("안제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("ahnjewoong@gmail.com")
                .phoneNumber("010-1234-5678")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        Member member2 = Member.builder()
                .studentId("C011118")
                .studentName("박제웅")
                .studentFeeStatus(StudentFeeStatus.PAID)
                .email("parkjewoong@gmail.com")
                .phoneNumber("010-5678-9012")
                .password("1234")
                .memberStatus(MemberStatus.ACTIVE)
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        LockerAssignment lockerAssignment1 = LockerAssignment.builder()
                .assignSemester("2025-2")
                .locker(locker1)
                .member(member1)
                .isCurrentSemester(true)
                .build();

        LockerAssignment lockerAssignment2 = LockerAssignment.builder()
                .assignSemester("2025-2")
                .locker(locker2)
                .member(member2)
                .isCurrentSemester(true)
                .build();

        lockerAssignmentRepository.save(lockerAssignment1);
        lockerAssignmentRepository.save(lockerAssignment2);
    }

    @AfterEach
    void tearDown() {
        var cache = cacheManager.getCache("lockers");
        if (cache != null) cache.clear();

        lockerAssignmentRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        lockerRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("A 구역 사물함 전체 조회 성공")
    void getAllLockers_getSection_A_API() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get("/lockers")
                .param("lockerSection", "A")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result[0].lockerNumber").value("A1"))
                .andExpect(jsonPath("$.result[0].studentId").value("C011117"))
                .andExpect(jsonPath("$.result[0].studentName").value("안제웅"))
                .andExpect(jsonPath("$.result[1].lockerNumber").value("A2"))
                .andExpect(jsonPath("$.result[1].studentId").value("C011118"))
                .andExpect(jsonPath("$.result[1].studentName").value("박제웅"))
                .andExpect(jsonPath("$.result[2].lockerNumber").value("A3"))
                .andExpect(jsonPath("$.result[2].lockerStatus").value("BROKEN"))
                .andExpect(jsonPath("$.result[3].lockerNumber").value("A4"))
                .andExpect(jsonPath("$.result[3].lockerStatus").value("AVAILABLE"));
    }

    @Test
    @DisplayName("나의 사물함 조회 성공")
    void getMyLocker_API() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get("/lockers/me")
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.lockerId").value(1L))
                .andExpect(jsonPath("$.result.lockerNumber").value("A1"))
                .andExpect(jsonPath("$.result.lockerRentalStatus").value(LockerRentalStatus.RENTING.name()));
    }

}
