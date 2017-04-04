package com.extraware.xwtemplate.cache;

import java.io.IOException;
import java.io.Reader;

/**
 * P XwTemplate lê os ficheiros de template através de objetos que implementam este interface,
 * ou seja os templates não precisam de ser ficheiros reais, e podem ser qualquer tipo de fonte
 * (como classpath, contexto de servlet, base de dados, etc). Apesar de existirem implementações
 * pré-instaladas, é normal que criem as suas próprias implementações.
 *
 * Para inserir o CarregadorTemplate utilizado, usar Configuracao.setCarregadorTemplate(CarregadorTemplate).
 *
 * As implementações deste interfade deverão ser à prova de threads.
 *
 */
public interface CarregadorTemplate {

    /**
     * Este método procura um objeto que age como uma fonte do template com o nome fornecido.
     * Este método é chamado através do CacheTemplate quando um template é pedido, antes de chamar,
     * ou o getUltimoModificado(Object) ou o getLeitor(Object, String).
     *
     * @param nome Nome do template
     * @return Um objeto representando a fonte do template
     * @throws IOException
     */
    public Object procurarFonteTemplate(String nome) throws IOException;

    /**
     * Este método retorna a hora da última modificação de determinada fonte de template.
     * Este método é chamado depois do procurarFonteTemplate(String).
     *
     * @param fonteTemplate Um objeto representando uma fonte de template obtido pelo método
     *                      procurarFonteTemplate(String)
     * @return A hora da última modificação da fonte de template especificada ou -1 se não conhecido.
     */
    public long getUltimoModificado(Object fonteTemplate);

    /**
     * Obter o stream de caractéres de um template representado por uma fonte de template específica.
     * Este método é chamado depois do procurarFonteTemplate(String) se a cópia em cache do template
     * estiver indisponível ou parada.
     *
     * @param fonteTemplate Um objeto representado uma fonte de template, obtido através de uma
     *                      chamada anterior ao procurarFonteTemplate(String).
     * @param codificacao A codificação dos caractéres utilizado para traduzir os bytes fornecidos em caractéres.
     * @return Um leitor representando um stream de caractéres do template.
     * @throws IOException Enviado se houver um erro de acesso ao stream.
     */
    public Reader getLeitor(Object fonteTemplate, String codificacao) throws IOException;

    /**
     * Fecha a fonte do template. Este é o último método que é chamado pela CacheTemplate para a fonte de template.
     * A framework garante que este método é chamado em cada objeto que é devolvido pelo procurarFonteTemplate(String).
     *
     * @param fonteTemplate A fonte de template que deverá ser fechada.
     * @throws IOException
     */
    public void fecharFonteTemplate(Object fonteTemplate) throws IOException;
}
