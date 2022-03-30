package com.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.is.database.pool.ConnectionPool;
import com.is.model.FirstRequest.Data;
import com.is.model.FirstRequest.KatmReq;
import com.is.model.FirstRequest.Security;

import com.is.model.Response.Kresponce;
import com.is.model.SecondRequest.KatmReqS;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Locale;



public class Api extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String url_;
    private static String alias;
    private static String branch;
    NumberFormat nf = NumberFormat.getInstance(Locale.ROOT);

    private static String pLogin;
    private static String pPassword;

    public Api() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            pLogin = ConnectionPool.getValue("Login");

            pPassword = ConnectionPool.getValue("Password");

            url_ = ConnectionPool.getValue("URL");
            System.out.println("URL: " + url_);
            alias = ConnectionPool.getValue("SCHEMA");
            System.out.println("ALIAS: " + alias);
            branch = ConnectionPool.getValue("BRANCH");
            System.out.println("BRANCH: " + branch);
        } catch (Exception e) {
            e.printStackTrace();
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();

        String pHead = request.getParameter("pHead");
        String pCode = request.getParameter("pCode");
        String pLegal = request.getParameter("pLegal");
        String pClaimId = request.getParameter("pClaimId");
        String pReportId = request.getParameter("pReportId");
        String pReportFormat = request.getParameter("pReportFormat");

        KatmReq katmReq = new KatmReq(new Security(pLogin, pPassword),
                new Data(pHead, pCode, pLegal, pClaimId, Integer.parseInt(pReportId), Integer.parseInt(pReportFormat)));

        //String respl = getResp("/credit/report", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(katmReq));

        Kresponce responsen = mapper.readValue(getResponse("/credit/report", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(katmReq)), Kresponce.class);

        if (responsen.getData().getResult().equals("05050")) {

            ISLogger.getLogger().info("responsen.getData().getResult(): " + responsen.getData().getResult());

            KatmReqS katmReqS = new KatmReqS();
            com.is.model.SecondRequest.Data datas = new com.is.model.SecondRequest.Data();
            datas.setPClaimId(pClaimId);
            datas.setPCode(pCode);
            datas.setPHead(pHead);
            datas.setPReportFormat(Integer.parseInt(pReportFormat));
            datas.setPToken(responsen.getData().getToken());

            katmReqS.setData(datas);

            try {
                Thread.sleep(66000);
            } catch (InterruptedException e) {
                ISLogger.getLogger().info("Thread: " + e);
            }


            ISLogger.getLogger()
                    .info("1 katmReqS: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(katmReqS));

            Kresponce responces = mapper.readValue(getResponse("/credit/report/status",
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(katmReqS)), Kresponce.class);

            while (responces.getData().getResult().equals("05050")) {
                ISLogger.getLogger().info("While: responsen.getData().getResult(): " + responsen.getData().getResult());

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    ISLogger.getLogger().info("Thread: " + e);
                }

                ISLogger.getLogger()
                        .info("2 katmReqS: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(katmReqS));

                responces = mapper.readValue(getResponse("/credit/history/status",
                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(katmReqS)), Kresponce.class);
            }

            if (responces.getData().getResult().equals("05000")) {

                byte[] rptbs = Base64.getDecoder().decode(responces.getData().getReportBase64());
                ISLogger.getLogger().info("BASE64:rpts   " + new String(rptbs, "UTF-8"));

                String reportStr = new String(rptbs, "UTF-8");
                System.out.println("REPORTSTR: " + reportStr);

                response.getWriter().append("K#S");

                List<String> reportlist = Arrays.asList(reportStr.split("\n"));

                ISLogger.getLogger().info("curr_all_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_all_count\">"));
                ISLogger.getLogger().info("curr_all_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_all_amount\">"));

                ISLogger.getLogger().info("curr_open_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_open_count\">"));
                ISLogger.getLogger().info("curr_open_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_open_amount\">"));

                ISLogger.getLogger().info("curr_percent_open_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_percent_open_count\">"));
                ISLogger.getLogger().info("curr_percent_open_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_percent_open_amount\">"));

                ISLogger.getLogger().info("curr_overdue_30_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_count\">"));
                ISLogger.getLogger().info("curr_overdue_30_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_amount\">"));

                ISLogger.getLogger().info("curr_overdue_30_60_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_60_count\">"));
                ISLogger.getLogger().info("curr_overdue_30_60_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_60_amount\">"));

                ISLogger.getLogger().info("curr_overdue_60_90_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_60_90_count\">"));
                ISLogger.getLogger().info("curr_overdue_60_90_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_60_90_amount\">"));

                ISLogger.getLogger().info("curr_overdue_90_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_90_count\">"));
                ISLogger.getLogger().info("curr_overdue_90_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_90_amount\">"));

                ISLogger.getLogger().info("curr_court_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_court_count\">"));
                ISLogger.getLogger().info("curr_court_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_court_amount\">"));

                ISLogger.getLogger().info("curr_bad_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_bad_count\">"));
                ISLogger.getLogger().info("curr_bad_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_bad_amount\">"));

                ISLogger.getLogger().info(
                        "score_point: " + findInList(reportlist, "<td style=\"width: 196px;\" id=\"score_point\">"));

                String average_monthly_payment1 = findInList2(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: left; width: 177px;\" colspan=\"4\">");
                String average_monthly_payment3 = average_monthly_payment1.equals("0") ? "0" : average_monthly_payment1;

                ISLogger.getLogger()
                        .info("average_monthly_payment1: " + average_monthly_payment1.equals(" ") + " ,real_list"
                                + average_monthly_payment1 + ", average_monthly_payment3: " + average_monthly_payment3);
                ISLogger.getLogger()
                        .info("new_Api_112233: " + "new_Api2_112233: " + average_monthly_payment1.equals(" ")
                                + ", compare with 0 " + average_monthly_payment1.equals("0") + " , null "
                                + average_monthly_payment1.equals(null));

                Connection c = null;
                PreparedStatement ps = null;

                try {
                    c = ConnectionPool.getConnection();
                    ps = c.prepareStatement("insert into katm_analyze "
                            + "(BRANCH, CLAIM_ID, DATA, CLIENT, K_VSE_V_ZAD, S_VSE_V_ZAD, K_D_BEZ_PROS, S_D_BEZ_PROS, K_PROS_PROC, S_PROS_PROC, K_PROS_D30, S_PROS_D30, K_PROS_D60, S_PROS_D60, K_PROS_D90, S_PROS_D90, K_SUD, S_SUD, K_SPIS, S_SPIS, SRED_MES_PLAT, SKOR_BAL, K_PROS_D60_90, S_PROS_D60_90, K_PERESMOTR, S_PERESMOTR,"
                            + " CLS_K_VSE_V_ZAD, CLS_S_VSE_V_ZAD, CLS_K_D_BEZ_PROS, CLS_S_D_BEZ_PROS, CLS_K_PROS_PROC, CLS_S_PROS_PROC, CLS_K_PROS_D30, CLS_S_PROS_D30, CLS_K_PROS_D60, CLS_S_PROS_D60, CLS_K_PROS_D90, CLS_S_PROS_D90, CLS_K_SUD, CLS_S_SUD, CLS_K_SPIS, CLS_S_SPIS, CLS_K_PROS_D60_90, CLS_S_PROS_D60_90, CLS_K_PERESMOTR, CLS_S_PERESMOTR) "
                            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                            + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


                    ps.setString(1, pCode);
                    ps.setString(2, pClaimId);
                    ps.setString(3, reportStr);
                    ps.setString(4, "");

                    // Open

                    ps.setInt(5, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_all_count\">"))); // k_vse_v_zad

                    ps.setDouble(6, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_all_amount\">")
                            .replace(",", ""))); // s_vse_v_zad

                    ps.setInt(7, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_open_count\">"))); // k_d_bez_pros

                    ps.setDouble(8, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_open_amount\">")
                            .replace(",", ""))); // s_d_bez_pros

                    ps.setInt(9, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_percent_open_count\">"))); // k_pros_proc

                    ps.setInt(10, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_percent_open_amount\">")
                            .replace(",", ""))); // s_pros_proc

                    ps.setInt(11, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_count\">")));

                    ps.setInt(12, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_amount\">")
                            .replace(",", "")));

                    ps.setInt(13, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_60_count\">")));
                    ps.setInt(14, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_60_amount\">")
                            .replace(",", "")));

                    ps.setInt(15, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_90_count\">")));
                    ps.setInt(16, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(17, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_court_count\">")));
                    ps.setInt(18, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_court_amount\">")
                            .replace(",", "")));

                    ps.setInt(19, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_bad_count_od\">")));
                    ps.setInt(20, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_bad_amount_od\">")
                            .replace(",", "")));

                    ps.setInt(21, Integer.valueOf(average_monthly_payment1.replaceAll("\\D+", "")));
                    ps.setInt(22,
                            Integer.valueOf(findInList(reportlist, "<td style=\"width: 196px;\" id=\"score_point\">")));

                    ps.setInt(23, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_60_90_count\">")));
                    ps.setInt(24, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_60_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(25, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_revise_count\">")));
                    ps.setInt(26, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_revise_amount\">")
                            .replace(",", "")));

                    // Close

                    ps.setInt(27, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_all_count\">"))); // k_vse_v_zad

                    ps.setDouble(28, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_all_amount\">")
                            .replace(",", ""))); // s_vse_v_zad

                    ps.setInt(29, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_open_count\">"))); // k_d_bez_pros

                    ps.setDouble(30, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_open_amount\">")
                            .replace(",", ""))); // s_d_bez_pros

                    ps.setInt(31, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_percent_closed_count\">"))); // k_pros_proc

                    ps.setInt(32, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_percent_closed_amount\">")
                            .replace(",", ""))); // s_pros_proc

                    ps.setInt(33, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_30_count\">")));

                    ps.setInt(34, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_30_amount\">")
                            .replace(",", "")));

                    ps.setInt(35, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_30_60_count\">")));
                    ps.setInt(36, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_30_60_amount\">")
                            .replace(",", "")));

                    ps.setInt(37, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_90_count\">")));
                    ps.setInt(38, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(39, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_court_count\">")));
                    ps.setInt(40, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_court_amount\">")
                            .replace(",", "")));

                    ps.setInt(41, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_bad_count_od\">")));
                    ps.setInt(42, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_bad_amount_od\">")
                            .replace(",", "")));

                    ps.setInt(43, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_60_90_count\">")));
                    ps.setInt(44, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_60_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(45, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_revise_count\">")));
                    ps.setInt(46, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_revise_amount\">")
                            .replace(",", "")));
                    ps.executeUpdate();
                    c.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                    ISLogger.getLogger().error("Oshibka_pri_Insert_katm: " + e.getMessage());
                    response.getWriter().append("ERROR4 " + e.getMessage());
                } finally {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ConnectionPool.close(c);
                }

            } else {
                response.getWriter().append(
                        "ERROR1 " + responces.getData().getResult() + " " + responces.getData().getResultMessage());
                ISLogger.getLogger().error("Oshibka_pri__katm: " + responces.getData().getResultMessage());
            }

        } else {

            if (responsen.getData().getResult().equals("05000")) {

                byte[] rptb = Base64.getDecoder().decode(responsen.getData().getReportBase64());
                ISLogger.getLogger().info("BASE64:rptb   " + new String(rptb, "UTF-8"));

                String reportStr = new String(rptb, "UTF-8");
                System.out.println("REPORTSTR: " + reportStr);

                response.getWriter().append("K#S");


                List<String> reportlist = Arrays.asList(reportStr.split("\n"));


                ISLogger.getLogger().info("curr_all_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_all_count\">"));
                ISLogger.getLogger().info("curr_all_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_all_amount\">"));

                ISLogger.getLogger().info("curr_open_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_open_count\">"));
                ISLogger.getLogger().info("curr_open_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_open_amount\">"));

                ISLogger.getLogger().info("curr_percent_open_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_percent_open_count\">"));
                ISLogger.getLogger().info("curr_percent_open_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_percent_open_amount\">"));

                ISLogger.getLogger().info("curr_overdue_30_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_count\">"));
                ISLogger.getLogger().info("curr_overdue_30_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_amount\">"));

                ISLogger.getLogger().info("curr_overdue_30_60_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_60_count\">"));
                ISLogger.getLogger().info("curr_overdue_30_60_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_60_amount\">"));

                ISLogger.getLogger().info("curr_overdue_60_90_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_60_90_count\">"));
                ISLogger.getLogger().info("curr_overdue_60_90_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_60_90_amount\">"));

                ISLogger.getLogger().info("curr_overdue_90_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_90_count\">"));
                ISLogger.getLogger().info("curr_overdue_90_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_90_amount\">"));

                ISLogger.getLogger().info("curr_court_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_court_count\">"));
                ISLogger.getLogger().info("curr_court_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_court_amount\">"));

                ISLogger.getLogger().info("curr_bad_count: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_bad_count\">"));
                ISLogger.getLogger().info("curr_bad_amount: " + findInList(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_bad_amount\">"));

                ISLogger.getLogger().info(
                        "score_point: " + findInList(reportlist, "<td style=\"width: 196px;\" id=\"score_point\">"));

                String average_monthly_payment2 = findInList2(reportlist,
                        "<td style=\"border: 1px solid #02497f; text-align: left; width: 177px;\" colspan=\"4\">");
                String average_monthly_payment4 = average_monthly_payment2.equals("0") ? "0" : average_monthly_payment2;

                ISLogger.getLogger()
                        .info("average_monthly_payment2: " + average_monthly_payment2.equals(" ") != null ? "0"
                                : average_monthly_payment2 + ",average_monthly_payment4:" + average_monthly_payment4);
                ISLogger.getLogger()
                        .info("new_Api_112233: " + "new_Api2_112233: " + average_monthly_payment2.equals("")
                                + ", compare with 0 " + average_monthly_payment2.equals("0") + " , null "
                                + average_monthly_payment2.equals(null));
                Connection c = null;
                PreparedStatement ps = null;

                try {
                    c = ConnectionPool.getConnection();
                    ps = c.prepareStatement("insert into katm_analyze "
                            + "(BRANCH, CLAIM_ID, DATA, CLIENT, K_VSE_V_ZAD, S_VSE_V_ZAD, K_D_BEZ_PROS, S_D_BEZ_PROS, K_PROS_PROC, S_PROS_PROC, K_PROS_D30, S_PROS_D30, K_PROS_D60, S_PROS_D60, K_PROS_D90, S_PROS_D90, K_SUD, S_SUD, K_SPIS, S_SPIS, SRED_MES_PLAT, SKOR_BAL, K_PROS_D60_90, S_PROS_D60_90, K_PERESMOTR, S_PERESMOTR,"
                            + " CLS_K_VSE_V_ZAD, CLS_S_VSE_V_ZAD, CLS_K_D_BEZ_PROS, CLS_S_D_BEZ_PROS, CLS_K_PROS_PROC, CLS_S_PROS_PROC, CLS_K_PROS_D30, CLS_S_PROS_D30, CLS_K_PROS_D60, CLS_S_PROS_D60, CLS_K_PROS_D90, CLS_S_PROS_D90, CLS_K_SUD, CLS_S_SUD, CLS_K_SPIS, CLS_S_SPIS, CLS_K_PROS_D60_90, CLS_S_PROS_D60_90, CLS_K_PERESMOTR, CLS_S_PERESMOTR) "
                            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                            + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


                    ps.setString(1, pCode);
                    ps.setString(2, pClaimId);
                    ps.setString(3, reportStr);
                    ps.setString(4, "");

                    // Open

                    ps.setInt(5, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_all_count\">"))); // k_vse_v_zad

                    ps.setDouble(6, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_all_amount\">")
                            .replace(",", ""))); // s_vse_v_zad

                    ps.setInt(7, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_open_count\">"))); // k_d_bez_pros

                    ps.setDouble(8, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_open_amount\">")
                            .replace(",", ""))); // s_d_bez_pros

                    ps.setInt(9, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_percent_open_count\">"))); // k_pros_proc

                    ps.setInt(10, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_percent_open_amount\">")
                            .replace(",", ""))); // s_pros_proc

                    ps.setInt(11, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_count\">")));

                    ps.setInt(12, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_amount\">")
                            .replace(",", "")));

                    ps.setInt(13, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_30_60_count\">")));
                    ps.setInt(14, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_30_60_amount\">")
                            .replace(",", "")));

                    ps.setInt(15, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_90_count\">")));
                    ps.setInt(16, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(17, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_court_count\">")));
                    ps.setInt(18, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_court_amount\">")
                            .replace(",", "")));

                    ps.setInt(19, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_bad_count_od\">")));
                    ps.setInt(20, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_bad_amount_od\">")
                            .replace(",", "")));

                    ps.setInt(21, Integer.valueOf(average_monthly_payment2.replaceAll("\\D+", "")));
                    ps.setInt(22,
                            Integer.valueOf(findInList(reportlist, "<td style=\"width: 196px;\" id=\"score_point\">")));

                    ps.setInt(23, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_overdue_60_90_count\">")));
                    ps.setInt(24, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_overdue_60_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(25, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 177px;\" id=\"curr_revise_count\">")));
                    ps.setInt(26, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 134px;\" id=\"curr_revise_amount\">")
                            .replace(",", "")));

                    /////// Close

                    ps.setInt(27, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_all_count\">"))); // k_vse_v_zad

                    ps.setDouble(28, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_all_amount\">")
                            .replace(",", ""))); // s_vse_v_zad

                    ps.setInt(29, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_open_count\">"))); // k_d_bez_pros

                    ps.setDouble(30, Double.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_open_amount\">")
                            .replace(",", ""))); // s_d_bez_pros

                    ps.setInt(31, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_percent_closed_count\">"))); // k_pros_proc

                    ps.setInt(32, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_percent_closed_amount\">")
                            .replace(",", ""))); // s_pros_proc

                    ps.setInt(33, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_30_count\">")));

                    ps.setInt(34, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_30_amount\">")
                            .replace(",", "")));

                    ps.setInt(35, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_30_60_count\">")));
                    ps.setInt(36, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_30_60_amount\">")
                            .replace(",", "")));

                    ps.setInt(37, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_90_count\">")));
                    ps.setInt(38, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(39, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_court_count\">")));
                    ps.setInt(40, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_court_amount\">")
                            .replace(",", "")));

                    ps.setInt(41, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_bad_count_od\">")));
                    ps.setInt(42, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_bad_amount_od\">")
                            .replace(",", "")));

                    ps.setInt(43, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_overdue_60_90_count\">")));
                    ps.setInt(44, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_overdue_60_90_amount\">")
                            .replace(",", "")));

                    ps.setInt(45, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 157px;\" id=\"paid_revise_count\">")));
                    ps.setInt(46, Integer.valueOf(findInList(reportlist,
                            "<td style=\"border: 1px solid #02497f; text-align: center; width: 105px;\" id=\"paid_revise_amount\">")
                            .replace(",", "")));
                    ps.executeUpdate();
                    c.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                    ISLogger.getLogger().error("Oshibka_pri_Insert_katm1: " + e.getMessage());
                    response.getWriter().append("ERROR5 " + e.getMessage());
                } finally {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ConnectionPool.close(c);
                }

            } else {
                response.getWriter().append(
                        "ERROR2 " + responsen.getData().getResult() + " " + responsen.getData().getResultMessage());
            }

        }

        response.getWriter().append(responsen.getData().getResult());
        response.getWriter().flush();
    }


    private String getResponse(String mt, String data) {

        String message = "";
        int responseCode = 0;

        try {
            URL url = new URL(url_ + mt);
            System.out.println("URL = " + url);
            ISLogger.getLogger().info("url = " + url_ + mt);
            ISLogger.getLogger().info("data = " + data);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

            ObjectMapper mapper = new ObjectMapper();
            String output = "";
            output = mapper.writeValueAsString(data);
            ISLogger.getLogger().info("output" + output);

            wr.writeBytes(data);
            wr.flush();
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer stringBuffer = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    stringBuffer.append(new String(inputLine.getBytes(), "utf-8"));
                }
                in.close();
                message = stringBuffer.toString();
                ISLogger.getLogger().info("Response: " + message);
            } else {
                message = connection.getResponseMessage() + ", code: " + responseCode;
            }

            ISLogger.getLogger().error("responce code " + responseCode + "  body " + message);
        } catch (Exception e) {
            e.printStackTrace();
            ISLogger.getLogger().error("responseCode " + responseCode + " " + ConnectionPool.getPstr(e));
        }
        return message;
    }

    public String getRespVal(String sour, String key) {
        // int strt=sour.indexOf(key)+key.length()+1;
        int strt = sour.indexOf(key) + key.length();
        int end = (sour.indexOf("}", strt) == -1) ? strt : sour.indexOf("}", strt) - 2;
        return strt < 2 ? "0" : sour.substring(strt, end);
    }

    private static String find(String sour, String key) {

        sour = sour.trim().replaceAll("\n", "").replace(" ", "");

        int end_of_key = sour.indexOf(key) + key.length();
        int end_of_json = sour.indexOf("<", end_of_key);

        String span_value = sour.substring(end_of_key, end_of_json).replaceAll("\"", "");

        return span_value;
    }

    private static String findInList(List<String> sourList, String key) {

        key = key.trim().replaceAll("\n", "");
        String span = "<span>";

        for (int i = 0; i < sourList.size(); i++) {

            if (sourList.get(i).contains(key)) {

                int end_of_key = sourList.get(i + 1).indexOf(span) + span.length();
                int end_of_json = sourList.get(i + 1).indexOf("<", end_of_key);

                return sourList.get(i + 1).substring(end_of_key, end_of_json).replaceAll("\"", "");
            }
        }

        return null;
    }

    private static String findInList2(List<String> sourList, String key) {

        key = key.trim().replaceAll("\n", "");
        String span = "<span>";

        for (int i = 0; i < sourList.size(); i++) {

            if (sourList.get(i).contains(key)) {
                if (sourList.get(i + 1).indexOf(span) != -1) {

                    int end_of_key = sourList.get(i + 1).indexOf(span) + span.length();
                    int end_of_json = sourList.get(i + 1).indexOf("<", end_of_key);

                    ISLogger.getLogger()
                            .info("end_of_key: " + end_of_key + ",end_of_json:" + end_of_json + ", return : "
                                    + sourList.get(i + 1).substring(end_of_key, end_of_json).replaceAll("\"", ""));

                    return sourList.get(i + 1).substring(end_of_key, end_of_json).replaceAll("\"", "");
                }

                else {
                    ISLogger.getLogger().info("sec_part: " + "0");
                    return "0";
                }
            }
        }

        return "0";
    }



}
