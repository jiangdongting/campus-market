cat > /data/workspace/myshixun/src/Transform.java <<'EOF'
import java.sql.*;

public class Transform {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL =
            "jdbc:mysql://127.0.0.1:3306/sparsedb?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    static final String USER = "root";
    static final String PASS = "123123";

    /**
     * 向sc表中插入数据
     */
    public static int insertSC() {

        Connection connection = null;
        PreparedStatement queryStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;

        int count = 0;

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(
                    DB_URL,
                    USER,
                    PASS
            );

            String querySql =
                    "SELECT * FROM entrance_exam ORDER BY sno";

            queryStmt = connection.prepareStatement(querySql);
            rs = queryStmt.executeQuery();

            String insertSql =
                    "INSERT INTO sc(sno, col_name, col_value) VALUES (?, ?, ?)";

            insertStmt = connection.prepareStatement(insertSql);

            String[] columns = {
                    "chinese",
                    "math",
                    "english",
                    "physics",
                    "chemistry",
                    "biology",
                    "history",
                    "geography",
                    "politics"
            };

            while (rs.next()) {

                int sno = rs.getInt("sno");

                for (String column : columns) {

                    Object value = rs.getObject(column);

                    if (value != null) {

                        insertStmt.setInt(1, sno);
                        insertStmt.setString(2, column);
                        insertStmt.setString(3, value.toString());

                        count += insertStmt.executeUpdate();
                    }
                }
            }

            return count;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;

        } finally {

            try {
                if (rs != null) rs.close();
                if (queryStmt != null) queryStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        insertSC();
    }
}
EOF

echo "✅ Transform.java 写入完成，数据库已改为 sparsedb，可以提交评测"