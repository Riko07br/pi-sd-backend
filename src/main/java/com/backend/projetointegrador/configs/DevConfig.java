package com.backend.projetointegrador.configs;

import com.backend.projetointegrador.domain.entities.Account;
import com.backend.projetointegrador.domain.entities.Investment;
import com.backend.projetointegrador.domain.entities.Product;
import com.backend.projetointegrador.domain.entities.Role;
import com.backend.projetointegrador.domain.entities.Transaction;
import com.backend.projetointegrador.domain.entities.User;
import com.backend.projetointegrador.repositories.AccountRepository;
import com.backend.projetointegrador.repositories.InvestmentRepository;
import com.backend.projetointegrador.repositories.ProductRepository;
import com.backend.projetointegrador.repositories.RoleRepository;
import com.backend.projetointegrador.repositories.TransactionRepository;
import com.backend.projetointegrador.repositories.UserRepository;
import com.backend.projetointegrador.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Configuration
@Profile({"dev", "seed"})
public class DevConfig implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.save(new Role(null, "ADMIN"));
        Role clientRole = roleRepository.save(new Role(null, "CLIENT"));

        String password = securityConfiguration.passwordEncoder().encode("123456");

        User u1 = userRepository.save(new User(null, "user1@mail.com", password, clientRole));
        User u2 = userRepository.save(new User(null, "user2@mail.com", password, clientRole));
        User u3 = userRepository.save(new User(null, "user3@mail.com", password, clientRole));
        User u4 = userRepository.save(new User(null, "user4@mail.com", password, clientRole));
        User u5 = userRepository.save(new User(null, "user5@mail.com", password, clientRole));
        User u6 = userRepository.save(new User(null, "user6@mail.com", password, clientRole));

        Account acc1 = accountRepository.save(new Account(null, "Cliente da Silva", "123456", "11912345678", LocalDate.of(1999, 10, 30), 0f, u3));
        Transaction t1 = new Transaction(null, 1020f, "DEPOSIT");
        transactionRepository.save(t1);
        t1.setBalance(acc1.getBalance());
        t1.getBalance().addBalance(1020f);
        transactionRepository.save(t1);
        Account acc2 = accountRepository.save(new Account(null, "Ronilso Junior Junior", "123456", "11912346678", LocalDate.of(2000, 3, 28), 0f, u4));
        Account acc3 = accountRepository.save(new Account(null, "Account 3", "123456", "11912347678", LocalDate.of(2001, 8, 13), 0f, u5));

        Product p1 = productRepository.save(new Product(null, "Pix Buzzard 30 dias", .001f, "Serviço exclusivo para acompanhar todas as movimentações do Pix Buzzard por 30 dias. Ideal para quem busca insights financeiros detalhados e em tempo real."));
        Product p2 = productRepository.save(new Product(null, "Pix Buzzard 60 dias", .0015f, "Extensão do serviço Pix Buzzard com duração de 60 dias. Mais tempo para monitorar e analisar movimentações financeiras com praticidade e precisão."));
        Product p3 = productRepository.save(new Product(null, "Pix Buzzard 90 dias", .002f, "Acompanhe todas as movimentações do Pix Buzzard por 90 dias. Solução ideal para controle financeiro prolongado e estratégico."));
        Product p4 = productRepository.save(new Product(null, "Poupança programada 360 dias", .005f, "Planeje e poupe de forma inteligente com nosso serviço de poupança programada para 360 dias. Segurança e rentabilidade ao seu alcance."));
        Product p5 = productRepository.save(new Product(null, "Investimento Flex 120 dias", .003f, "Flexibilidade e segurança em um plano de investimento de 120 dias. Ideal para objetivos de curto a médio prazo."));
        Product p6 = productRepository.save(new Product(null, "Pix Turbo 15 dias", .0005f, "Monitoramento intensivo e acelerado de movimentações Pix por 15 dias. Rápido, eficiente e acessível."));
        Product p7 = productRepository.save(new Product(null, "Rendimento Garantido 180 dias", .004f, "Aumente seu patrimônio com nosso serviço de rendimento garantido por 180 dias. A escolha certa para crescimento financeiro."));
        Product p8 = productRepository.save(new Product(null, "Poupança Avançada 720 dias", .005f, "Uma solução robusta para poupar e crescer. Poupança planejada para 720 dias com segurança e alta rentabilidade."));
        Product p9 = productRepository.save(new Product(null, "Pix Observer 45 dias", .0012f, "Acompanhe movimentações Pix com detalhes por 45 dias. Perfeito para análises financeiras específicas e detalhadas."));

        Investment i1 = investmentRepository.save(new Investment(null, 100f, getPastDate(10), acc1, p1));
        Investment i2 = investmentRepository.save(new Investment(null, 200f, getPastDate(4), acc1, p2));
        Investment i3 = investmentRepository.save(new Investment(null, 300f, getPastDate(2), acc1, p3));
    }

    private Instant getPastDate(int days) {
        return Instant.now().minus(days, ChronoUnit.DAYS);
    }

    private Instant getFutureDate(int days) {
        return Instant.now().plus(days, ChronoUnit.DAYS);
    }
}
