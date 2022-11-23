package client.clients;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import com.sep3yg9.assignment2.client.Main;
import com.sep3yg9.assignment2.grpc.protobuf.parts.PartTray;
import com.sep3yg9.assignment2.grpc.protobuf.products.PartProduct;
import com.sep3yg9.assignment2.grpc.protobuf.products.Product;
import com.sep3yg9.assignment2.grpc.protobuf.products.ProductList;
import com.sep3yg9.assignment2.grpc.protobuf.products.ProductServiceGrpc;
import com.sep3yg9.assignment2.grpc.protobuf.trays.Tray;
import com.sep3yg9.assignment2.grpc.protobuf.trays.TrayList;
import io.grpc.ManagedChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductClient
{
  private final Scanner scanner = new Scanner(System.in);
  private final List<String> productOptions = new ArrayList<>(List.of(
      "1", "2", "3", "4", "5", "0"
  ));

  private final ProductServiceGrpc.ProductServiceBlockingStub stub;

  public ProductClient(ManagedChannel channel)
  {
    this.stub = ProductServiceGrpc.newBlockingStub(channel);
  }

  public void productManagement() {
    String option = "";

    do {
      System.out.println("Select option:");
      System.out.println("1 - Create product");
      System.out.println("2 - Put part into product");
      System.out.println("3 - Mark product as finished");
      System.out.println("4 - View all available trays");
      System.out.println("5 - View all products");
      System.out.println("0 - Back to main menu");
      System.out.print(": ");
      option = scanner.nextLine();
    } while (!productOptions.contains(option));

    switch (option) {
      case "1": {
        createProduct();
      }
      case "2": {
        putIntoProduct();
      }
      case "3": {
        markProductAsFinished();
      }
      case "4": {
        viewAvailableTrays();
      }
      case "5": {
        viewAvailableProducts();
      }
      case "0": {
        Main.mainMenu();
      }
    }
  }

  public void createProduct() {
    System.out.println("------------------------------------------");
    System.out.println("Product creation:");
    System.out.println("------------------------------------------");

    System.out.println("Available options: ");
    System.out.println("\"Same parts\" - product consisting only of same type parts");
    System.out.println("\"Half an animal\" - product consisting only of unique type parts");
    System.out.println("Type in products type: ");
    String type = scanner.nextLine();

    Product productCreated = stub.createProduct(StringValue.newBuilder().setValue(type).build());
    System.out.println("------------------------------------------");
    System.out.println(productCreated + "\n");
    productManagement();
  }

  public void putIntoProduct() {
    System.out.println("------------------------------------------");
    System.out.println("Put part into product:");
    System.out.println("------------------------------------------");

    System.out.println("Type in product id: ");
    long productId = Long.parseLong(scanner.nextLine());

    System.out.println("Type in part id: ");
    long partId = Long.parseLong(scanner.nextLine());

    Product product = stub.putIntoProduct(PartProduct.newBuilder().setProductId(productId).setPartId(partId).build());
    System.out.println("------------------------------------------");
    System.out.println(product + "\n");
    productManagement();
  }

  public void markProductAsFinished() {
    System.out.println("------------------------------------------");
    System.out.println("Mark product as finished:");
    System.out.println("------------------------------------------");

    System.out.println("Type in product id: ");
    long productId = Long.parseLong(scanner.nextLine());

    Product product = stub.markProductAsFinished(Int64Value.newBuilder().setValue(productId).build());
    System.out.println("------------------------------------------");
    System.out.println(product + "\n");
    productManagement();
  }

  public void viewAvailableTrays() {
    System.out.println("------------------------------------------");
    System.out.println("All trays and their contents:");
    System.out.println("------------------------------------------");
    TrayList trayList = stub.getAllFinishedTrays(Empty.newBuilder().build());
    System.out.println(trayList + "\n");
    productManagement();
  }

  public void viewAvailableProducts() {
    System.out.println("------------------------------------------");
    System.out.println("All products and their contents:");
    System.out.println("------------------------------------------");
    ProductList productList = stub.getAllProducts(Empty.newBuilder().build());
    System.out.println(productList + "\n");
    productManagement();
  }
}
