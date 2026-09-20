cat > /data/workspace/myshixun/src/Transfer.java <<'EOF'
import java.sql.*;
import java.util.Scanner;

public class Transfer {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/finance?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    static final String USER = "root";
    static final String PASS = "123123";

    /**
     * 转账操作
     *
     * @param connection 数据库连接对象
     * @param sourceCard 转出账号
     * @param destCard 转入账号
     * @param amount 转账金额
     * @return boolean
     *   true  - 转账成功
     *   false - 转账失败
     */
    public static boolean transferBalance(Connection connection,
                                          String sourceCard,
                                          String destCard,
                                          double amount) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 关闭自动提交，开启事务
            connection.setAutoCommit(false);

            // 1. 查询转出账户
            String sql = "SELECT b_type, b_balance FROM bank_card WHERE b_number = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, sourceCard);
            rs = pstmt.executeQuery();

            // 转出账户不存在
            if (!rs.next()) {
                connection.rollback();
                return false;
            }

            String sourceType = rs.getString("b_type").trim();
            double sourceBalance = rs.getDouble("b_balance");

            rs.close();
            pstmt.close();
            rs = null;
            pstmt = null;

            // 2. 转出账户不能是信用卡
            if ("信用卡".equals(sourceType)) {
                connection.rollback();
                return false;
            }

            // 3. 转出账户余额不足
            if (sourceBalance < amount) {
                connection.rollback();
                return false;
            }

            // 4. 查询转入账户
            sql = "SELECT b_type FROM bank_card WHERE b_number = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, destCard);
            rs = pstmt.executeQuery();

            // 转入账户不存在
            if (!rs.next()) {
                connection.rollback();
                return false;
            }

            String destType = rs.getString("b_type").trim();

            rs.close();
            pstmt.close();
            rs = null;
            pstmt = null;

            // 5. 扣除转出账户余额
            sql = "UPDATE bank_card SET b_balance = b_balance - ? WHERE b_number = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setString(2, sourceCard);

            if (pstmt.executeUpdate() != 1) {
                connection.rollback();
                return false;
            }

            pstmt.close();
            pstmt = null;

            // 6. 更新转入账户
            if ("信用卡".equals(destType)) {
                // 信用卡余额表示已透支金额
                // 还款时透支金额减少
                sql = "UPDATE bank_card SET b_balance = b_balance - ? WHERE b_number = ?";
            } else {
                // 储蓄卡正常增加余额
                sql = "UPDATE bank_card SET b_balance = b_balance + ? WHERE b_number = ?";
            }

            pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, amount);
            pstmt.setString(2, destCard);

            if (pstmt.executeUpdate() != 1) {
                connection.rollback();
                return false;
            }

            // 7. 提交事务
            connection.commit();
            return true;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return false;

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                // 恢复自动提交
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 不要修改main()
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        Class.forName(JDBC_DRIVER);

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

        while(sc.hasNext())
        {
            String input = sc.nextLine();
            if(input.equals(""))
                break;

            String[]commands = input.split(" ");
            if(commands.length ==0)
                break;

            String payerCard = commands[0];
            String payeeCard = commands[1];
            double amount = Double.parseDouble(commands[2]);

            if (transferBalance(connection, payerCard, payeeCard, amount)) {
                System.out.println("转账成功。" );
            } else {
                System.out.println("转账失败,请核对卡号，卡类型及卡余额!");
            }
        }
    }
}
EOF

echo "✅ Transfer.java 写入完成，可以直接提交评测"