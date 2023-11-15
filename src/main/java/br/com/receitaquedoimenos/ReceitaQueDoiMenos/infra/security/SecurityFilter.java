package br.com.receitaquedoimenos.ReceitaQueDoiMenos.infra.security;

import br.com.receitaquedoimenos.ReceitaQueDoiMenos.repositories.UserRepository;
import br.com.receitaquedoimenos.ReceitaQueDoiMenos.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de segurança para processar tokens de autenticação em cada requisição.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    /**
     * Executa o filtro de segurança para validar e processar o token de autenticação.
     *
     * @param request     O objeto HttpServletRequest contendo a requisição.
     * @param response    O objeto HttpServletResponse contendo a resposta.
     * @param filterChain O objeto FilterChain para encadear a execução de outros filtros.
     * @throws ServletException Se ocorrer uma exceção relacionada ao Servlet.
     * @throws IOException      Se ocorrer uma exceção de E/S.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);

        if(token != null) {
            String email = tokenService.validateToken(token);
            UserDetails userDetails = userRepository.findByEmail(email);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token de autenticação do cabeçalho da requisição.
     *
     * @param request O objeto HttpServletRequest contendo a requisição.
     * @return O token de autenticação recuperado, ou null se não estiver presente.
     */
    private String recoverToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeader == null) return null;

        return authenticationHeader.replace("Bearer ", "");
    }
}
