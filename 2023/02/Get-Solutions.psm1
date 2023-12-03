# ipmo -For .\Get-Solutions.psm1; Get-Solutions
[CmdletBinding]
function Get-Solutions() {
    $result1 = Get-Solution1 -RedCount 12 -GreenCount 13 -BlueCount 14
    Write-Output($result1)
    $result2 = Get-Solution2
    Write-Output($result2)
}

function Get-GameNumber() {
    param(
        [string]$Str
    )
    $gameRegex = "Game ([0-9]+):"
    if ($str -match $gameRegex) {
        $num = $Matches.1
        return [int]$num
    }
    Write-Error -Message "Could not retrieve game number"
}

function Confirm-ValidRound() {
    param(
        [Parameter()]
        [string]$Str,
        
        [Parameter()]
        [int]$RedMax,
    
        [Parameter()]
        [int]$GreenMax,
    
        [Parameter()]
        [int]$BlueMax
    )
    $redRegex = "(?<Count>[0-9]+) red"
    $greenRegex = "(?<Count>[0-9]+) green"
    $blueRegex = "(?<Count>[0-9]+) blue"
    
    if (($Str -match $redRegex) -and ([int]$Matches.Count -gt $RedMax)) {
        return $False
    }

    if (($Str -match $greenRegex) -and ([int]$Matches.Count -gt $GreenMax)) {
        return $False
    }

    if (($Str -match $blueRegex) -and ([int]$Matches.Count -gt $BlueMax)) {
        return $False
    }

    return $True
}

function Get-Solution1() {
    param(
        [Parameter()]
        [int]$RedCount,

        [Parameter()]
        [int]$GreenCount,

        [Parameter()]
        [int]$BlueCount
    )

    $successfulGames = 0
    foreach ($line in Get-Content .\input.txt) {
        $currentGame = Get-GameNumber($line)
        $successfulRoundFlag = $True
        $allRounds = ($line -split ":")[1]
        $rounds = ($allRounds -split ";")
        foreach ($round in $rounds) {
            $isValid = $True
            $isValid = Confirm-ValidRound -Str $round -RedMax $RedCount -GreenMax $GreenCount -BlueMax $BlueCount
            if ($isValid -eq $False) {
                $successfulRoundFlag = $False
                break
            }
        }
        if ($successfulRoundFlag -eq $True) {
            $successfulGames += $currentGame
        }
    }

    return $successfulGames
}

function Get-MaxColours() {
    param(
        [Parameter()]
        [string]$Str,
        
        [Parameter()]
        [ref]$RedMax,
    
        [Parameter()]
        [ref]$GreenMax,
    
        [Parameter()]
        [ref]$BlueMax
    )
    $redRegex = "(?<Count>[0-9]+) red"
    $greenRegex = "(?<Count>[0-9]+) green"
    $blueRegex = "(?<Count>[0-9]+) blue"
    
    if (($Str -match $redRegex) -and ([int]$Matches.Count -gt $RedMax.value)) {
        $RedMax.value = [int]$Matches.Count
    }

    if (($Str -match $greenRegex) -and ([int]$Matches.Count -gt $GreenMax.value)) {
        $GreenMax.value = [int]$Matches.Count
    }

    if (($Str -match $blueRegex) -and ([int]$Matches.Count -gt $BlueMax.value)) {
        $BlueMax.value = [int]$Matches.Count
    }
}

function Get-Solution2() {
    $result = 0
    foreach ($line in Get-Content .\input.txt) {
        $highestRed, $highestGreen, $highestBlue = 0
        $allRounds = ($line -split ":")[1]
        $rounds = ($allRounds -split ";")
        foreach ($round in $rounds) {
            Get-MaxColours -Str $round -RedMax ([ref] $highestRed) -GreenMax ([ref] $highestGreen) -BlueMax ([ref] $highestBlue)
        }
        $result += $highestRed * $highestGreen * $highestBlue
    }
    return $result
}
