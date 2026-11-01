public class Driver {
    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";
        String[] keys = {"name", "id"};
        String[] values = {"Riya", "A07"};

        String filled = TemplateFiller.fill(template, keys, values);
        System.out.println(filled);
    }
}
