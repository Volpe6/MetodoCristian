# Método de Cristian para a sincronização de relógios
### Sincronização de relógios 
- Relógios sincronizados = valores próximos uns dos outros e próximos da hora real (global) 
- Sincronização externa: Método de Cristian, estar sincronizado a fonte externa de tempo universal (UTC). Neste algoritmo não foi feita a sincronização com uma fonte externa, pois para exemplificar o método era necessário alterar o horário do servidor
- Sincronização interna: Algoritmo de Berkeley, obter precisão relativamente a um tempo interno ao sistema 
- Obs: Sincronização externa ⇒ sincronização interna
### Método de Cristian - Exemplo
![](/imgs/cristianImagem.PNG)
fonte: [Sincronização](http://www.di.ubi.pt/~pprata/sdtf/SDTF_10_11_T02_TempoRelogios.pdf)
- No momento t0 o cliente solicita o horário para o
servidor;
- Onde envia a resposta informando que h=21:00
- T1 momento em que recebe a Resposta.
- P=(20:36-20:32)/2=00:02
- Relógio do cliente sincronizar para 21:02
### Método de Cristian - Problemas
- Ponto único de falha e congestionamento;
- Servidor em falha ou malicioso;
- Tempo não anda para trás;
