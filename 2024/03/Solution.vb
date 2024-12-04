Imports System.Text.RegularExpressions

' Code developed using https://onecompiler.com/vb/42zxh5cne

Public Module Program
   Sub calculateMatches(text As String, expr As String)
      Dim mc As MatchCollection = Regex.Matches(text, expr, RegexOptions.Multiline)
      Dim m As Match
      Dim total As Integer
      Dim matches As Integer = 0
      
      For Each m In mc
        Dim lv As Integer = Convert.toInt32(m.Groups(1).Value)
        Dim rv As Integer = Convert.toInt32(m.Groups(2).Value)
        total = total + lv * rv
      Next m
      
      Console.WriteLine(total)
   End Sub
   
  Sub solution1(name As String)
    calculateMatches(name, "mul\((\d{1,3}),(\d{1,3})\)")
  End Sub
  
	Public Sub Main(args() As string)
	  Dim input as String = Console.ReadLine()
	  solution1(input)
	End Sub
End Module