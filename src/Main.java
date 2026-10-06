import com.welltrack.dao.WellnessParameterDAO;

public class Main {

    public static void main(String[] args) {

        WellnessParameterDAO dao =
                new WellnessParameterDAO();

        boolean result = dao.saveParameters(
                8.0,   // Maximum work hours
                1.0,   // Minimum break hours
                60     // Minimum activity level
        );

        if (result) {
            System.out.println("Wellness Parameters Saved Successfully!");
        } else {
            System.out.println("Wellness Parameters Save Failed!");
        }
    }
}