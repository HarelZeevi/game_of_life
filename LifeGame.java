import java.util.Scanner;

public class LifeGame
{
    static char[][] matrix;
    static char[][] next;
    static int gens;
    
    // טענת כניסה: הפעולה מקבלת כפרמטרים אינדקס שורה ואינדקס עמודה.        
    // טענת יציאה: הפעולה מחזירה כמה שכנים חיים יש לתא זה.        
    public static int liveNeighboursNum(int row, int col)
    {
        int count = 0;
        int rowfrom = Math.max(row - 1, 0);
        int rowto = Math.min(row + 1, matrix.length - 1);
        int colfrom = Math.max(col - 1, 0);
        int colto = Math.min(col + 1, matrix[0].length - 1);
        for (int i = rowfrom; i <= rowto; i++)
        {
            for (int j = colfrom; j <= colto; j++)
            {
                //if it's not me and there are life here
                if ((i != row || j != col) && matrix[i][j] == 'O')
                    count++;
            }
        }
        return count;
    }

    // טענת כניסה: הפעולה משתמשת במשתנים גלובלים, אין לה פרמטרים.        
    // טענת יציאה: הפעולה מעדכנת את המטריצה next מתוך המטריצה matrix ובעזרת הפעולה הקודמת.         
    public static void updateNextGen()
    {
        int neighbours;
        char cellStatus;
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                neighbours = liveNeighboursNum(i, j);
                cellStatus = matrix[i][j]; 
                
                if (cellStatus == ' ')
                {
                    if (neighbours == 3)
                        // A dead cell will born again if it has 3 alive neighbours.
                        next[i][j] = 'O'; 
                }
                else
                {
                    if (neighbours < 2 || neighbours > 3)
                    {
                        // A cell will die if it has less than 2 or more than 3 alive neighbours.
                        next[i][j] = ' ';
                    }
                    else
                    {
                        // if none of this had happened than the cell has 2 or 3
                        // neighbours and it'll keep living
                        next[i][j] = 'O';
                    }
                }
            } 
        }
        
    }
    
    // טענת כניסה: הפעולה משתמשת במשתנים גלובלים, אין לה פרמטרים.        
    // טענת יציאה: הפעולה מעתיקה את הערכים ממטריצה הראשונה לשניה.        
    public static void copyMatrix()
    {
        for (int i = 0; i < matrix.length; i++)
        {
             matrix[i] = next[i].clone();
        }
    }
    
      // טענת כניסה: הפעולה משתמשת במשתנים גלובלים, אין לה פרמטרים.        
    // טענת יציאה: הפעולה מבצעת מעבר דור אחד במשחק, לפי הכללים הנתונים, ובעזרת הפעולות הקודמות.        
    public static void nextGeneration()
    {
        updateNextGen();
        copyMatrix();
        displayMatrix();
    }
    
     // טענת כניסה: הפעולה משתמשת במשתנים גלובלים, אין לה פרמטרים.         
    // טענת יציאה: הפעולה המציגה את מטריצת החיים matrix באותו רגע.        
    public static void displayMatrix()
    {
        for (int i = 0; i < matrix.length; i++)
        {
            System.out.println();
            for (int j = 0; j < matrix[0].length; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
        }
    }
    
     // טענת כניסה: הפעולה משתמשת במשתנים גלובלים, אין לה פרמטרים.         
    // טענת יציאה: הפעולה מאתחלת את המטריצה matrix באופן הבא:        
// עבור כל תא במערך יוגרל מספר שלם בין 0 ל-4 . אם יצא 0 - התא יקבל ערך של חיים 'O', אחרת  - התא יקבל רווח '  '.        
    public static void initMatrix()
    {
        int randNum;
        char sign;
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                sign = ' ';
                randNum = (int)(Math.random() * 5);
                if (randNum == 0)
                    sign = 'O';
                matrix[i][j] = sign;
            }
        }
    }
    public static void init_next()
    {
        for (int i = 0; i < next.length; i++)
        {
            for (int j = 0; j < next[0].length; j++)
            {
                next[i][j] = ' ';
            }
        }
    }
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int width, height;
        
        System.out.println("Enter matix width (at least 20): ");
        width = input.nextInt();
        System.out.println("Enter matix height (at least 40): ");
        height = input.nextInt();
        
        while (width < 20 || height < 40)
        {
              System.out.println("Invalid input. ");
              System.out.println("Enter matix width (at least 20): ");
              width = input.nextInt();
              System.out.println("Enter matix height (at least 40): ");
              height = input.nextInt();
        }
        matrix = new char[height][width];
        next = new char[height][width];
        init_next();
        System.out.println("Enter number of generations(4 - 100): ");
        gens = input.nextInt();
        
        initMatrix();
        System.out.println("\n#Generation 1:");
        displayMatrix();
        
        for (int i = 2; i <= gens; i++)
        {
            System.out.println("\n#Generation "+ i + ":");
            nextGeneration();
        }
    }
}
