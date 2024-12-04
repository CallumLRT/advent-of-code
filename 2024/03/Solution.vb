Imports System.IO
Imports System.Text.RegularExpressions

Public Module Program
    Private Function calculateLineMatches(text As String, expr As String) As Integer
        Dim mc As MatchCollection = Regex.Matches(text, expr, RegexOptions.Multiline)
        Dim m As Match
        Dim lineTotal As Integer
        Dim matches As Integer = 0
        
        For Each m In mc
            Dim leftVal As Integer = Convert.toInt32(m.Groups(1).Value)
            Dim rightVal As Integer = Convert.toInt32(m.Groups(2).Value)
            lineTotal = lineTotal + leftVal * rightVal
        Next m
        
        Return lineTotal
    End Function
    
    Private Function solution1() As Integer
        Dim total As Integer
        
        For Each line As String In File.ReadLines("input.txt")
            total += calculateLineMatches(line, "mul\((\d{1,3}),(\d{1,3})\)")
        Next line

        Return total
    End Function
  
    Public Sub Main(args() As string)
        Console.WriteLine(solution1())
    End Sub
End Module