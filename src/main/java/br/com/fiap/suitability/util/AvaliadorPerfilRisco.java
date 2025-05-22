package br.com.fiap.suitability.util;

import br.com.fiap.suitability.model.Risco;
import java.util.Map;
import java.util.HashMap;


public class AvaliadorPerfilRisco {

    public static Risco avaliar(int conservadoras, int moderadas, int agressivas) {
        if (agressivas > moderadas && agressivas > conservadoras) {
            return Risco.AGRESSIVO;
        } else if (moderadas > agressivas && moderadas > conservadoras) {
            return Risco.MODERADO;
        } else if (conservadoras > moderadas && conservadoras > agressivas) {
            return Risco.CONSERVADOR;
        } else if (agressivas == moderadas && agressivas > conservadoras) {
            return Risco.AGRESSIVO;
        } else if (moderadas == conservadoras && moderadas > agressivas) {
            return Risco.MODERADO;
        } else if (agressivas == conservadoras && agressivas > moderadas) {
            return Risco.AGRESSIVO;
        }

        // Triplo empate ou empate total
        return Risco.MODERADO;
    }


    public static Map<String, String> recomendarCarteira(Risco risco) {
        Map<String, String> carteira = new HashMap<>();
        switch (risco) {
            case CONSERVADOR:
                carteira.put("Renda Fixa", "80%");
                carteira.put("Fundos de Investimento", "20%");
                break;
            case MODERADO:
                carteira.put("Renda Fixa", "50%");
                carteira.put("Renda Variável", "30%");
                carteira.put("Fundos de Investimento", "20%");
                break;
            case AGRESSIVO:
                carteira.put("Renda Fixa", "20%");
                carteira.put("Renda Variável", "60%");
                carteira.put("Fundos Imobiliários", "20%");
                break;
        }
        return carteira;
    }

}
