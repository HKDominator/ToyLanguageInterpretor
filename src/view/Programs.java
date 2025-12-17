package view;

import controller.Controller;
import controller.IController;
import model.adt.list.IGenericList;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepo;
import repository.Repo;

import java.util.LinkedList;

public class Programs {
    private LinkedList<IController> initialStatementsControllers;
    public Programs(){
        initialStatementsControllers = new LinkedList<>();
        IStatement ex1 = new ComposedStatements(
                new VariableDeclarationStatement("v", new IntType()),
                new ComposedStatements(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatements(new VariableExpression("v"))
                ));
        IRepo repo1 = new Repo(ex1, "log1.txt");
        IController controller1 = new Controller(repo1);

        IStatement ex2 = new ComposedStatements(
                new VariableDeclarationStatement("a", new IntType()),
                new ComposedStatements(
                        new VariableDeclarationStatement("b", new IntType()),
                        new ComposedStatements(
                                new AssignmentStatement("a",
                                        new ArithmeticExpression('+',
                                                new ValueExpression(new IntValue(2)),
                                                new ArithmeticExpression('*',
                                                        new ValueExpression(new IntValue(3)),
                                                        new ValueExpression(new IntValue(5))
                                                )
                                        )

                                ),
                                new ComposedStatements(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression('+',
                                                        new VariableExpression("a"),
                                                        new ValueExpression( new IntValue(1))
                                                )
                                        ),
                                        new PrintStatements(new VariableExpression("b"))
                                )
                        )
                )
        );
        IRepo repo2 = new Repo(ex2, "log2.txt");
        IController controller2 = new Controller(repo2);

        IStatement ex3 = new ComposedStatements(
                new VariableDeclarationStatement("a", new BoolType()),
                new ComposedStatements(
                        new VariableDeclarationStatement("v", new BoolType()),//
                        new ComposedStatements(//
                                new AssignmentStatement("a",
                                        new ValueExpression(new BoolValue(true))
                                ),//
                                new ComposedStatements(
                                        new IfStatement(
                                                new VariableExpression("a"),//
                                                new AssignmentStatement("v",
                                                        new ValueExpression(new IntValue(2))
                                                ),
                                                new AssignmentStatement("v",
                                                        new ValueExpression( new IntValue(3)))),
                                        new PrintStatements(new VariableExpression("v"))
                                )
                        )
                )
        );
        IRepo repo3 = new Repo(ex3, "log3.txt");
        IController controller3 = new Controller(repo3);

        IStatement ex4 = new ComposedStatements(
                new VariableDeclarationStatement("varf", new StringType()),
                new ComposedStatements(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new ComposedStatements(
                                new OpenReadFile(new VariableExpression("varf")),
                                new ComposedStatements(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new ComposedStatements(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new ComposedStatements(
                                                        new PrintStatements(new VariableExpression("varc")),
                                                        new ComposedStatements(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new ComposedStatements(
                                                                        new PrintStatements(new VariableExpression("varc")),
                                                                        new CloseReadFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        IRepo repo4 = new Repo(ex4, "log4.txt");
        IController controller4 = new Controller(repo4);

        // Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
        IStatement ex5 = new ComposedStatements(
                // Ref int v;
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new ComposedStatements(
                        // new(v, 20);
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new ComposedStatements(
                                // Ref Ref int a;
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new ComposedStatements(
                                        // new(a, v);
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new ComposedStatements(
                                                // print(v);
                                                new PrintStatements(new VariableExpression("v")),
                                                // print(a);
                                                new PrintStatements( new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );

        IRepo repo5 = new Repo(ex5, "log5.txt");
        IController controller5 = new Controller(repo5);

        IStatement ex6 = new ComposedStatements(
                // int v;
                new VariableDeclarationStatement("v", new IntType()),
                new ComposedStatements(
                        // v = 4;
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new ComposedStatements(
                                // while (v > 0)
                                new WhileStatement(
                                        new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                        // Body of while: { print(v); v = v - 1; }
                                        new ComposedStatements(
                                                new PrintStatements(new VariableExpression("v")),
                                                new AssignmentStatement("v",
                                                        new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))
                                                )
                                        )
                                ),
                                // print(v) (after the loop)
                                new PrintStatements(new VariableExpression("v"))
                        )
                )
        );

        IRepo repo6 = new Repo(ex6, "log6.txt");
        IController controller6 = new Controller(repo6);

        IStatement ex7 = new ComposedStatements(
                // Ref int v;
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new ComposedStatements(
                        // new(v, 20);
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new ComposedStatements(
                                // Ref Ref int a;
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new ComposedStatements(
                                        // new(a, v);
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new ComposedStatements(
                                                // new(v, 30);
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                // print(rH(rH(a)));
                                                new PrintStatements(
                                                        new HeapReadingExpression(
                                                                new HeapReadingExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IRepo repo7 = new Repo(ex7, "log7.txt");
        IController controller7 = new Controller(repo7);

        initialStatementsControllers.add(controller1);
        initialStatementsControllers.add(controller2);
        initialStatementsControllers.add(controller3);
        initialStatementsControllers.add(controller4);
        initialStatementsControllers.add(controller5);
        initialStatementsControllers.add(controller6);
        initialStatementsControllers.add(controller7);
    }

    public IController getStatementController(int idx){
        return initialStatementsControllers.get(idx - 1);
    }
}
