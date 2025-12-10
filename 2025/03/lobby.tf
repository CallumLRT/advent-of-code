provider "terraform" {}

terraform {
  required_version = "~> 1.14"
}

locals {
  file = chomp(file("./input.txt"))

  lines = [for line in split("\n", local.file) : flatten([for battery in split("", line) : tonumber(battery)])]

  largest_lhs_battery_per_line = [for line in local.lines : max(slice(line, 0, length(line) - 1)...)]

  leftmost_index_of_largest_battery = [for idx, line in local.lines : index(line, local.largest_lhs_battery_per_line[idx])]

  largest_rhs_battery_per_line = [for idx, line in local.lines : max(slice(line, local.leftmost_index_of_largest_battery[idx] + 1, length(line))...)]
}

output "solution_1_result" {
  value = sum([for idx, battery in local.largest_lhs_battery_per_line : format("%d%d", battery, local.largest_rhs_battery_per_line[idx])])
}

locals {
  # This is kinda horrible, but it works around the error: `Local value cannot use its own result as part of its expression.` - i.e. loops are stateless
  # we can only get around this by creating our own provider in another language (but in the spirit of getting a Terraform solution, this is the cleanest approach)

  # Map index of the largest available => the battery joltage
  sub_11 = [for idx, line in local.lines : {
    index   = index(slice(line, 0, length(line) - 11), max(slice(line, 0, length(line) - 11)...)),
    joltage = max(slice(line, 0, length(line) - 11)...)
  }]

  # Because we're working with slices, each iteration is actually indexed as a delta from the last found battery
  # We need to add the previous index because of this
  sub_10 = [for idx, line in local.lines : {
    index   = 1 + local.sub_11[idx].index + index(slice(line, local.sub_11[idx].index + 1, length(line) - 10), max(slice(line, local.sub_11[idx].index + 1, length(line) - 10)...)),
    joltage = max(slice(line, local.sub_11[idx].index + 1, length(line) - 10)...)
  }]

  sub_9 = [for idx, line in local.lines : {
    index   = 1 + local.sub_10[idx].index + index(slice(line, local.sub_10[idx].index + 1, length(line) - 9), max(slice(line, local.sub_10[idx].index + 1, length(line) - 9)...)),
    joltage = max(slice(line, local.sub_10[idx].index + 1, length(line) - 9)...)
  }]

  sub_8 = [for idx, line in local.lines : {
    index   = 1 + local.sub_9[idx].index + index(slice(line, local.sub_9[idx].index + 1, length(line) - 8), max(slice(line, local.sub_9[idx].index + 1, length(line) - 8)...)),
    joltage = max(slice(line, local.sub_9[idx].index + 1, length(line) - 8)...)
  }]

  sub_7 = [for idx, line in local.lines : {
    index   = 1 + local.sub_8[idx].index + index(slice(line, local.sub_8[idx].index + 1, length(line) - 7), max(slice(line, local.sub_8[idx].index + 1, length(line) - 7)...)),
    joltage = max(slice(line, local.sub_8[idx].index + 1, length(line) - 7)...)
  }]

  sub_6 = [for idx, line in local.lines : {
    index   = 1 + local.sub_7[idx].index + index(slice(line, local.sub_7[idx].index + 1, length(line) - 6), max(slice(line, local.sub_7[idx].index + 1, length(line) - 6)...)),
    joltage = max(slice(line, local.sub_7[idx].index + 1, length(line) - 6)...)
  }]

  sub_5 = [for idx, line in local.lines : {
    index   = 1 + local.sub_6[idx].index + index(slice(line, local.sub_6[idx].index + 1, length(line) - 5), max(slice(line, local.sub_6[idx].index + 1, length(line) - 5)...)),
    joltage = max(slice(line, local.sub_6[idx].index + 1, length(line) - 5)...)
  }]

  sub_4 = [for idx, line in local.lines : {
    index   = 1 + local.sub_5[idx].index + index(slice(line, local.sub_5[idx].index + 1, length(line) - 4), max(slice(line, local.sub_5[idx].index + 1, length(line) - 4)...)),
    joltage = max(slice(line, local.sub_5[idx].index + 1, length(line) - 4)...)
  }]

  sub_3 = [for idx, line in local.lines : {
    index   = 1 + local.sub_4[idx].index + index(slice(line, local.sub_4[idx].index + 1, length(line) - 3), max(slice(line, local.sub_4[idx].index + 1, length(line) - 3)...)),
    joltage = max(slice(line, local.sub_4[idx].index + 1, length(line) - 3)...)
  }]

  sub_2 = [for idx, line in local.lines : {
    index   = 1 + local.sub_3[idx].index + index(slice(line, local.sub_3[idx].index + 1, length(line) - 2), max(slice(line, local.sub_3[idx].index + 1, length(line) - 2)...)),
    joltage = max(slice(line, local.sub_3[idx].index + 1, length(line) - 2)...)
  }]

  sub_1 = [for idx, line in local.lines : {
    index   = 1 + local.sub_2[idx].index + index(slice(line, local.sub_2[idx].index + 1, length(line) - 1), max(slice(line, local.sub_2[idx].index + 1, length(line) - 1)...)),
    joltage = max(slice(line, local.sub_2[idx].index + 1, length(line) - 1)...)
  }]

  sub_0 = [for idx, line in local.lines : {
    index   = 1 + local.sub_1[idx].index + index(slice(line, local.sub_1[idx].index + 1, length(line)), max(slice(line, local.sub_1[idx].index + 1, length(line))...)),
    joltage = max(slice(line, local.sub_1[idx].index + 1, length(line))...)
  }]

  battery_format_template = join("", [for i in range(0, 12) : "%s"])

  battery_combination_per_line = [for idx, line in local.lines : tonumber(format(local.battery_format_template,
    local.sub_11[idx].joltage,
    local.sub_10[idx].joltage,
    local.sub_9[idx].joltage,
    local.sub_8[idx].joltage,
    local.sub_7[idx].joltage,
    local.sub_6[idx].joltage,
    local.sub_5[idx].joltage,
    local.sub_4[idx].joltage,
    local.sub_3[idx].joltage,
    local.sub_2[idx].joltage,
    local.sub_1[idx].joltage,
    local.sub_0[idx].joltage,
  ))]
}

output "solution_2_result" {
  value = sum(local.battery_combination_per_line)
}
