package com.example.ctrlgen.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SwitchWithLabel(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier

            .padding(horizontal = 6.dp) // Adjust horizontal padding
            .padding(vertical = 8.dp),  // Adjust vertical padding
        verticalAlignment = Alignment.CenterVertically, // Center items vertically
        horizontalArrangement = Arrangement.Start // Align items at the start
    ) {
        Text(
            text = label,
            modifier = Modifier.align(Alignment.CenterVertically) // Center text vertically
        )
        Spacer(modifier = Modifier.width(8.dp)) // Add a spacer to control the distance
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.align(Alignment.CenterVertically) // Center switch vertically
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SwitchWithLabelPreview() {
    SwitchWithLabel(
        label = "Sample Switch",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )
}
