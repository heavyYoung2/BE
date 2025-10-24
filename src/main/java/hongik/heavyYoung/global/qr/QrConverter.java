package hongik.heavyYoung.global.qr;

public class QrConverter {

    public static QrTokenResponseDTO toQrTokenResponseDTO(String qrToken, boolean isStudentFeePaid) {
        return QrTokenResponseDTO.builder()
                .qrToken(qrToken)
                .studentFeePaid(isStudentFeePaid)
                .build();
    }


}
