package hongik.heavyYoung.global.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 챗봇 프롬프트(대화 맥락) 생성 유틸리티 클래스.
 */
@Slf4j
public class PromptFactory {
    private static final String HEAVYYOUNG_SYSTEM_PROMPT = """
            넌 학생회비 통합 관리 플랫폼 회비영이야. 사람들은 나에게 학생회비와 관련된 질문을 해.
            나는 사용자가 학생회비와 관련된 질문을 하면, 최대한 친절하고 정확하게 답변해줘야 해.
            학생회비 납부여부 확인, 물품 대여, 공지사항, 사물함 관리 등 학생회비와 관련된 다양한 주제에 대해 답변할 수 있어야 해.
            너가 행사 참여를 위해, 학생회비 납부 여부를 확인하려면, 하단 메뉴바 첫번째 QR코드를 눌러서 확인할 수 있다고 안내해줘.
            물품 관련된 질문을 받으면, 하단 메뉴바 두번째 물품대여 탭에서 대여할 수 있다고 안내해줘.
            공지사항 관련된 질문을 받으면, 하단 메뉴바 세번째 공지사항 탭에서 확인할 수 있다고 안내해줘.
            사물함 관련된 질문을 받으면, 하단 메뉴바 네번째 사물함 탭에서 관리할 수 있다고 안내해줘.
            마이 페이지는 학생회비 납부, 개인정보 수정, 비밀번호 변경 등을 할 수 있는 곳이라고 안내해줘.
            항상 학생회비와 관련된 정보만 제공하고, 학생회비와 관련 없는 질문에는 답변하지 마.
        """;

    /**
     * OpenAI 모델 호출 시 사용할 옵션
     * model: 사용할 LLM 모델 이름
     * temperature: 창의성 조절 (0에 가까울수록 사실 기반, 할루시네이션 낮아짐)
     */
    private static final OpenAiChatOptions options = OpenAiChatOptions.builder()
            .model("gpt-4.1-mini")
            .temperature(0.7)
            .build();

    public static Prompt buildPrompt(List<Message> chatHistory) {
        Message systemMessage = SystemMessage.builder()
                .text(HEAVYYOUNG_SYSTEM_PROMPT)
                .build();

        List<Message> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.addAll(chatHistory);

        return Prompt.builder()
                .messages(messages)
                .chatOptions(options)
                .build();
    }
}
