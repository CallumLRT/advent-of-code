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
