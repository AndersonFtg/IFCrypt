# IFCrypt: Uma Solução para Criptografia de Documentos em Java

O IFCrypt foi desenvolvido como parte do Trabalho de Conclusão de Curso (TCC) do curso de Tecnologia em Analise e Desenvolvimento de Sistemas do Instituo Federal de São Paulo (IFSP) – Campus Campinas, para a obtenção do título de Tecnólogo em Analise e Desenvolvimento de Sistemas.
![telaHome_auto_x2](https://user-images.githubusercontent.com/48109661/122684061-b9307080-d1d9-11eb-9d5a-9f9532aa2d92.jpg)

O IFCrypt é uma ferramenta para criptografar e descriptografar arquivos em formato PDF utilizando o processo de envelopamento digital, além de gerar uma assinatura digital para o documento.

O IFCrypt funciona da seguinte forma: o usuário ao iniciar a aplicação precisa gerar um par de chaves RSA caso ainda não possua, em seguida ele seleciona a função de criptografar que recebe um arquivo como entrada que é cifrado com uma chave simétrica, que por sua vez, é cifrada com a chave pública do destinatário.

A execução do código pode ser feita pela IDE Eclipse. É necessário criar um projeto e importar as classes do pacote **src** e após isso o código pode ser modificado e a aplicação executada.

Os manuais de utilização estão disponíveis como: “IFCrypt – Manual do Usuário” e “IFCrypt – Em execução” que mostra, em vídeo, o sistema sendo executado.

O sistema foi desenvolvido utilizando o padrão de arquitetura de software MVC (Model-View-Controller – Modelo-Visão-Controlador). Portanto, foi dividido em três pacotes: **model**, **view** e **controller**. 

No pacote **model** existem outros quatro pacotes: criptografia, main, arquivo e util. No pacote criptografia estão as classes responsáveis pelas funcionalidades do sistema, são elas: CriptografarArquivo, DescriptografarArquivo, AssinaturaDigital e GerarChavesRSA. No pacote main está apenas a classe Main, responsável pelo início da aplicação. No pacote arquivo estão as classes SelecaoArquivo e ManipulacaoArquivo que foram empregadas, respectivamente, para o usuário selecionar o arquivo PDF e as chaves e para realizar as transformações necessárias nos arquivos. No pacote util está a classe MensagemAoUsuario que contém todas as mensagens que são exibidas no sistema, como mensagem de sucesso, erro, alerta etc e o enumerador ValoresFixos que contém todas as constantes, variáveis de valores fixos que não podem ser alterados, utilizadas no sistema. 

O pacote **view** contém as interfaces visuais do sistema desenhadas em formato FXML.

No pacote **controller** estão as classes responsáveis por fazer o intermédio entre as ações dos usuários na view e o retorno das classes do model, são elas: ControllerCriptografar, ControllerDescriptografar, ControllerGerarChavesRSA e ControllerPrincipal. As classes desse pacote são responsáveis por definir qual método, de uma determinada classe do pacote model, será executado ao realizar determinada ação como, por exemplo, clicar em um botão.