# Keep the glass tile exact and reproducible: 16x16 RGBA, with identical
# edge pixels so repeating faces do not draw a frame around each block.
Add-Type -AssemblyName System.Drawing

$rows = @(
    '................',
    '...a............',
    '....b...........',
    '.....c..........',
    '......b.........',
    '.......a........',
    '................',
    '................',
    '.............a..',
    '............b...',
    '...........c....',
    '..........b.....',
    '.........a......',
    '................',
    '................',
    '................'
)

$colors = @{
    '.' = [System.Drawing.Color]::FromArgb(48, 205, 238, 248)
    'a' = [System.Drawing.Color]::FromArgb(72, 172, 235, 249)
    'b' = [System.Drawing.Color]::FromArgb(104, 216, 250, 255)
    'c' = [System.Drawing.Color]::FromArgb(142, 247, 254, 255)
}

$bitmap = [System.Drawing.Bitmap]::new(16, 16, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb)
try {
    for ($y = 0; $y -lt 16; $y++) {
        if ($rows[$y].Length -ne 16) { throw "Invalid row width at $y" }
        for ($x = 0; $x -lt 16; $x++) {
            $bitmap.SetPixel($x, $y, $colors[[string]$rows[$y][$x]])
        }
    }

    $projectRoot = Split-Path -Parent $PSScriptRoot
    foreach ($version in @('forge-1.20.1', 'neoforge-1.21.1')) {
        $folder = Join-Path $projectRoot "$version/src/main/resources/assets/penetrable_platform/textures/block"
        New-Item -ItemType Directory -Path $folder -Force | Out-Null
        $bitmap.Save((Join-Path $folder 'seamless_glass.png'), [System.Drawing.Imaging.ImageFormat]::Png)
    }
} finally {
    $bitmap.Dispose()
}
