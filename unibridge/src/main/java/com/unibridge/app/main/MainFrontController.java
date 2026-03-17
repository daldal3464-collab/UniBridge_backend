package com.unibridge.app.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unibridge.app.Result;
import com.unibridge.app.main.controller.MainController;

/**
 * 메인 페이지 FrontController
 *
 * 위치  : com.unibridge.app.main (controller 밖)
 * web.xml 매핑: *.main
 *
 * 처리 URL 예시:
 *   GET /index.main  → 메인 페이지 렌더링 (main.jsp forward)
 */
public class MainFrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MainFrontController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String target     = extractTargetPath(requestURI);
        Result result     = new Result();

        switch (target) {
            case "index.main":
            case "/index.main":
                result = new MainController().execute(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "요청한 기능을 사용할 수 없습니다.");
                return;
        }

        if (result != null) {
            if (result.isRedirect()) {
                response.sendRedirect(result.getPath());
            } else {
                request.getRequestDispatcher(result.getPath()).forward(request, response);
            }
        }
    }

    /**
     * URI의 마지막 세그먼트를 추출합니다.
     * 예: /unibridge/index.main → index.main
     */
    private String extractTargetPath(String requestUri) {
        String[] splitedPaths = requestUri.split("/");
        return splitedPaths[splitedPaths.length - 1];
    }
}
