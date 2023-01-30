package com.example.springbootjdbctransaction.controller;

import com.example.springbootjdbctransaction.dao.BankAccountDAO;
import com.example.springbootjdbctransaction.exception.BankTransactionException;
import com.example.springbootjdbctransaction.form.SendMoneyForm;
import com.example.springbootjdbctransaction.model.BankAccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class MainController {

    @Autowired
    private BankAccountDAO bankAccountDAO;

//    @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String showBankAccounts(Model model) {
        List<BankAccountInfo> list = bankAccountDAO.getBankAccounts();

        model.addAttribute("accountInfos", list);

        return "accountPagae";

    }
//
//    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    @GetMapping("/sendMoney")
    public String viewSendMoneyPage(ModelMap map) {

        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);

        map.addAttribute("sendMoneyForm", form);

        return "sendMoneyPage";
    }
//
//    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    @PostMapping("/sendMoney")
    public String processSendMoney(ModelMap map, SendMoneyForm sendMoneyForm) {

        System.out.println("Send Money::" + sendMoneyForm.getAmount());
        log.info("Send Money");

        try {
            bankAccountDAO.sendMoney(sendMoneyForm.getFromAccountId(), //
                    sendMoneyForm.getToAccountId(), //
                    sendMoneyForm.getAmount());
        } catch (BankTransactionException e) {
            map.addAttribute("errorMessage", "Error: " + e.getMessage());
            log.error("Գումարը հաշվի մեջ  բավարար չէ");
            return "/sendMoneyPage";
        }
        return "redirect:/";
    }
}
