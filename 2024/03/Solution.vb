Imports System.IO
Imports System.Text.RegularExpressions

Public Module Program
    Private Function calculateMatches(text As String) As Integer
        Dim mc As MatchCollection = Regex.Matches(text, "mul\((\d{1,3}),(\d{1,3})\)")
        Dim m As Match
        Dim total As Integer
        
        For Each m In mc
            Dim leftVal As Integer = Convert.toInt32(m.Groups(1).Value)
            Dim rightVal As Integer = Convert.toInt32(m.Groups(2).Value)
            total += + leftVal * rightVal
        Next m
        
        Return total
    End Function

    Private Sub getInput(byRef text As String)
        For Each line As String In File.ReadLines("input.txt")
            text += line
        Next line
    End Sub
    
    Private Function solution1() As Integer
        Dim fullInput As String = ""
        getInput(fullInput)

        Return calculateMatches(fullInput)
    End Function

    Private Function findValidBlocks(text As String) As Integer
        Dim mc As MatchCollection = Regex.Matches(text, "do\(\)(.*?)don't\(\)")
        Dim m As Match
        Dim total As Integer
        
        For Each m In mc
            total += calculateMatches(m.Value)
        Next m
        
        Return total
    End Function

    Private Function solution2() As String
        Dim fullInput As String = "do()"
        getInput(fullInput)
        fullInput = fullInput + "don't()"

        Return findValidBlocks(fullInput)
    End Function
  
    Public Sub Main(args() As string)
        Console.WriteLine(solution1())
        Console.WriteLine(solution2())
    End Sub
End Module
