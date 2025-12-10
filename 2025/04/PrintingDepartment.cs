
class PrintingDepartment
{
    class Vector(int x, int y)
    {
        public readonly int x = x;
        public readonly int y = y;

        public bool IsOutOfBounds()
        {
            if (x < 0 || x >= RowLength)
            {
                return true;
            }

            if (y < 0 || y >= NumberOfLines)
            {
                return true;
            }

            return false;
        }
    }

    private static readonly Vector[] ADJACENT = [
        new Vector(-1, -1),
        new Vector(-1, 0),
        new Vector(-1, 1),
        new Vector(0, -1),
        new Vector(0, 1),
        new Vector(1, -1),
        new Vector(1, 0),
        new Vector(1, 1),
    ];

    private static int RowLength;
    private static int NumberOfLines;

    static void Main()
    {
        var inputFile = "input.txt";
        var workspacePath = "/workspaces/advent-of-code/2025/04/" + inputFile;
        var inputCharArray = FileToCharArray(workspacePath);

        RowLength = inputCharArray[0].Length;
        NumberOfLines = inputCharArray.Length;

        Console.WriteLine("Solution 1: " + SolutionOne(inputCharArray));
        Console.WriteLine("Solution 2: " + SolutionTwo(inputCharArray));
    }

    private static char[][] FileToCharArray(string filePath)
    {
        var lines = File.ReadAllLines(filePath);
        return [.. lines.Select(line => line.ToCharArray())];
    }

    private static bool LookAdjacently(char[][] input, Vector location, int adjacentLimit = 3)
    {
        var adjacentCount = 0;

        foreach (var direction in ADJACENT)
        {
            var lookupLocation = new Vector(location.x + direction.x, location.y + direction.y);

            if (lookupLocation.IsOutOfBounds())
            {
                continue;
            }

            adjacentCount += input[lookupLocation.y][lookupLocation.x] == '@' ? 1 : 0;

            if (adjacentCount > adjacentLimit)
            {
                return false;
            }
        }

        return true;
    }

    private static int SolutionOne(char[][] input)
    {
        return SolutionTwo(input, true);
    }

    private static int SolutionTwo(char[][] input, bool iterate = false)
    {
        var rollsRemoved = 0;
        var accessibleRolls = 0;

        do
        {
            accessibleRolls = 0;

            foreach (var (rowIndex, row) in input.Select((row, rowIndex) => (rowIndex, row)))
            {
                foreach (var (charIndex, character) in row.Select((character, charIndex) => (charIndex, character)))
                {
                    if (character == '@')
                    {
                        if (LookAdjacently(input, new Vector(charIndex, rowIndex)))
                        {
                            accessibleRolls += 1;
                            rollsRemoved += 1;
                            input[rowIndex][charIndex] = iterate ? '@' : '.';
                        }
                    }
                }
            }

            if (iterate)
            {
                return accessibleRolls;
            }

        } while (accessibleRolls > 0);

        return rollsRemoved;
    }
}
