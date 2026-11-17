package leilao;

// RNF04: Trata vendas sem serviços e com N serviços de maneira uniforme
public class FaturaComprador {
    public static void imprimir(ItemFatura fatura) {
        System.out.println("==================================================");
        System.out.println("FATURA DO COMPRADOR");
        System.out.println(fatura.getDescricao());
        System.out.println("--------------------------------------------------");
        System.out.printf("TOTAL A PAGAR: R$ %.2f\n", fatura.getValorTotal());
        System.out.println("==================================================");
    }
}
