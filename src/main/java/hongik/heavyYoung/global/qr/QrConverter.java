package hongik.heavyYoung.global.qr;

public class QrConverter {

    public static QrTokenResponse toQrTokenResponse(String qrToken, boolean isStudentFeePaid) {
        return QrTokenResponse.builder()
                .qrToken(qrToken)
                .studentFeePaid(isStudentFeePaid)
                .build();
    }


}
