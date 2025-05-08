import java.security.PublicKey;
import java.util.Objects;

public abstract class SuperUser {
    private String name;
    private int id;
    private static int currentId;

    public SuperUser(String name) {
        this.name = name;
        this.id = currentId++;
    }

    public SuperUser(String name, int id) {
        this.name = name;
        this.id = id;
        //if the current ID is less than this ID, offset the current ID to bigger so that future SuperUser objects will not have same ID
        if (currentId < this.id) {
            currentId = this.id + 1;
        }
    }

    /**
     * Returns a SuperUser from the export data
     *
     * @param exportData export data
     * @return a User or Admin with corresponding data members
     */
    public SuperUser fromExportData(String exportData) {
        String[] data = exportData.split(",");
        try {
            if (data[2] == "Ad") {
                return new Admin(data[0], Integer.parseInt(data[1]));
            } else {
                User user = new User(data[0], Integer.parseInt(data[1]));
                for (int i = 2; i < data.length; i++) {
                    Task exportedTask = Task.fromExportData(data[i]);
                    if (exportedTask != null) {
                        user.getTaskManager().addTask(exportedTask);
                    }
                }
                return user;
            }
        } catch (Exception e) {
            System.out.println("Invalid export data - " + exportData);
            return null;
        }
    }

    /**
     * Gets the CSV format of the data fields
     *
     * @return CSV format of data
     */
    public String getExportData() {
        return String.format("%s,%s", name, id);
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperUser superUser = (SuperUser) o;
        return id == superUser.id && Objects.equals(name, superUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
