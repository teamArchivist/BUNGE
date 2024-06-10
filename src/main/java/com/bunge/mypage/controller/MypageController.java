package com.bunge.mypage.controller;

import com.bunge.member.controller.MemberController;
import com.bunge.member.domain.Mail;
import com.bunge.member.domain.Member;
import com.bunge.member.service.MemberService;
import com.bunge.member.service.MemberServiceimpl;
import com.bunge.mypage.service.MypageSendEmail;
import com.bunge.mypage.service.MypageService;
import com.bunge.review.domain.Review;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.Savepoint;
import java.util.*;

@Controller
@RequestMapping(value = "/mypage")
public class MypageController {

    private  static  final Logger        logger = LoggerFactory.getLogger((MemberController.class));
    private                MemberService memberservice;
    private                MypageService mypageservice;
    private                PasswordEncoder passwordEncoder;
    private                MypageSendEmail mypagesendemail;
    @Value("${my.savefolder}")
    private String saveFolder;

    @Autowired
    public MypageController(MypageService  mypageservice, PasswordEncoder passwordEncoder,
                            MemberService memberservice, MypageSendEmail mypagesendemail) {
        this.mypageservice = mypageservice;
        this.passwordEncoder=passwordEncoder;
        this.memberservice=memberservice;
        this.mypagesendemail=mypagesendemail;
    }

    //마이 페이지 폼 이동
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/mypage")
    public ModelAndView  mypage(ModelAndView mav ,  Principal principal) {

        String id = principal.getName();
        if(id == null) {
            mav.addObject("message", "로그인 후 이용 가능합니다.");
            mav.setViewName("redirect:/login");
        }else {
            mav.setViewName("mypage/mypage");
        }
        return mav;
    }

    //내 정보 수정폼
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/myinfo")
    public ModelAndView myinfo(ModelAndView mav , Principal principal){

        String id = principal.getName();

        if(id == null) {
            mav.addObject("message", "로그인 후 이용 가능합니다.");
            mav.setViewName("redirect:login");
            logger.info("로그인 후 이용 가능");
        }else {
            Member member = mypageservice.memberinfo(id);
            mav.setViewName("mypage/myinfo");
            mav.addObject("memberinfo", member);
        }
        return mav;
    }
    //내 정보 수정 비밀번호 처리
    @PostMapping(value = "/pwd-update-process")
    public ModelAndView pwdUpdateProcess(Member member , ModelAndView mav, String id) {
        String encPassword = passwordEncoder.encode(member.getPwd());
        Member infomember= mypageservice.memberinfo(id);
        member.setPwd(encPassword);
        member.setId(id);

        boolean result = mypageservice.pwdupdate(member);
        if(result) {
            mav.addObject("message", "회원정보 수정이 완료되었습니다.");
            mav.addObject("infomember",infomember);
            mav.setViewName("redirect:myinfo");
        }else {
            mav.addObject("message","회원정보 수정이 실패했습니다.");
            mav.setViewName("error/403");
        }
        return mav;
    }
    //내정보 이메일 수정 인증처리
    @GetMapping(value = "/mymaildelivery")
    public ResponseEntity<String> maildelivery(@RequestParam("email") String email){
        Mail mail = new Mail();
        mail.setTo(email);
        mypagesendemail.mypagesendmail(mail);
        return ResponseEntity.ok(mail.getRandom());
    }

    private String fileDBName(String fileName, String saveFolder) {
        //새로운 폴더 이름 : 오늘 년+월+일
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);	//오늘 년도 구하기
        int month = c.get(Calendar.MONTH)+1;//오늘 월 구하기
        int date = c.get(Calendar.DATE);	//오늘 일 구하기

        String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
        logger.info(homedir);
        File path1 = new File(homedir);
        if (!(path1.exists())) {
            path1.mkdirs(); //새로운 폴더 생성
        }
        //난수 구하기
        Random r = new Random();
        int random = r.nextInt(100000000);

        /****** 확장자 구하기 시작******/
        int index = fileName.lastIndexOf(".");
        // 문자열에서 특정 문자열의 위치 값(index)를 반환합니다.
        // indexOf가 처음 발견되는 문자열에 대한 index를 반환하는 반면,
        // lastIndexOf는 마지막으로 발견되는 문자열이 index를 반환합니다.
        // (파일명에 점에 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴합니다.)
        logger.info("index = " + index);

        String fileExtension = fileName.substring(index +1);
        logger.info("fileExtension = " + fileExtension);
        /**** 확장자 구하기 끝 ****/

        //새로운 파일명
        String refileName = "bbs" + year + month + date + random + "." + fileExtension;
        logger.info("refileName"+refileName);

        //오라클 디비에 저장될 파일명
        //String fileDBName = "/" + year + month + "-" + date + "/" + refileName;
        String fileDBName = File.separator + year + "-" + month + "-" + date + File.separator + refileName;
        logger.info("fileDBName"+fileDBName);
        return fileDBName;
    }
    //내 정보 수정 처리
    @PostMapping(value = "/update-process")
    public ModelAndView updateProcess (Member member, ModelAndView mav,MultipartFile uploadfile,String check, String id,
                                       RedirectAttributes redirectAttributes) throws IOException {

        if (check != null && !check.equals("")) {
            member.setProfile_original(check);
            logger.info("프로필 그대로");
        } else {
            if (uploadfile != null && !uploadfile.isEmpty()) {
                logger.info("프로필 변경");
                String fileName = uploadfile.getOriginalFilename(); //원래 파일명
                member.setProfile_original(fileName);

                String fileDBName = fileDBName(fileName, saveFolder);
                uploadfile.transferTo(new File(saveFolder + fileDBName));
                member.setProfile(fileDBName);
            } else {
                member.setProfile("");
                member.setProfile_original("");
            }
        }

        boolean result = mypageservice.update(member);
        Member infomember= memberservice.memberinfo(id);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(infomember, infomember.getPassword(), infomember.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(result) {
            redirectAttributes.addFlashAttribute("message" , "회원정보 수정이 완료되었습니다.");
            redirectAttributes.addFlashAttribute("infomember", infomember);
            mav.setViewName("redirect:myinfo");
        }else {
            redirectAttributes.addFlashAttribute("message", "회원정보 수정이 실패했습니다.");
            mav.setViewName("error/403");
        }

        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/myreview")
    public Map<String, Object> myReview(Principal principal){
        String id = principal.getName();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Review> list = mypageservice.getMyReviewList(id);
        logger.info(list.toString());
        int listcount = mypageservice.getMyReviewListCount(id);
        map.put("list", list);
        map.put("listcount", listcount);
        return map;
    }
}
