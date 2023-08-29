package io.github.caimucheng.leaf.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.SdStorage
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.github.caimucheng.leaf.common.R

@Composable
fun PrivacyPolicy() {
    val context = LocalContext.current
    val language = stringResource(id = R.string.current_language)
    val text = remember(key1 = language) {
        context.assets.open("protocol/$language/PrivacyPolicy.txt")
            .bufferedReader()
            .use {
                it.readText()
            }
    }

    Text(
        text = parseProtocol(text),
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(
                rememberScrollState(),
            )
    )
}

@Composable
fun UserAgreement() {
    val context = LocalContext.current
    val language = stringResource(id = R.string.current_language)
    val text = remember(key1 = language) {
        context.assets.open("protocol/$language/UserAgreement.txt")
            .bufferedReader()
            .use {
                it.readText()
            }
    }

    Text(
        text = parseProtocol(text),
        fontSize = 16.sp,
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(
                rememberScrollState(),
            )
    )
}

@Composable
fun LaunchMode(launchMode: String, onClick: (type: String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())){
        RadioItem(
            imageVector = Icons.Filled.SdStorage,
            contentDescription = stringResource(id = R.string.sdcard),
            titleText = stringResource(id = R.string.launch_mode_external),
            descriptionText = stringResource(id = R.string.launch_mode_external_description),
            selected = launchMode == "external",
            onClick = { onClick("external") }
        )
        RadioItem(
            imageVector = Icons.Filled.Storage,
            contentDescription = stringResource(id = R.string.storage),
            titleText = stringResource(id = R.string.launch_mode_internal),
            descriptionText = stringResource(id = R.string.launch_mode_internal_description),
            selected = launchMode == "internal",
            onClick = { onClick("internal") }
        )
        RadioItem(
            imageVector = Icons.Filled.AdminPanelSettings,
            contentDescription = stringResource(id = R.string.root),
            titleText = stringResource(id = R.string.launch_mode_root),
            descriptionText = stringResource(id = R.string.launch_mode_root_description),
            selected = launchMode == "root",
            onClick = { onClick("root") }
        )
        val context = LocalContext.current
        val language = stringResource(id = R.string.current_language)
        val text = remember(key1 = language) {
            context.assets.open("protocol/$language/LaunchMode.txt")
                .bufferedReader()
                .use {
                    it.readText()
                }
        }
        Text(
            text = parseProtocol(text),
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RadioItem(
    imageVector: ImageVector,
    contentDescription: String,
    titleText: String,
    descriptionText: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 80.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp)
        ) {
            val (icon, radioButton, group) = createRefs()
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(icon) {
                        linkTo(parent.top, parent.bottom)
                        linkTo(
                            parent.start,
                            parent.end,
                            startMargin = 20.dp,
                            endMargin = 20.dp,
                            bias = 0f
                        )
                    }
            )
            RadioButton(
                selected = selected,
                onClick = null,
                enabled = false,
                colors = RadioButtonDefaults.colors(
                    disabledUnselectedColor = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                    disabledSelectedColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.constrainAs(radioButton) {
                    linkTo(parent.top, parent.bottom)
                    linkTo(icon.end, parent.end, startMargin = 20.dp, endMargin = 20.dp, bias = 1f)
                }
            )

            Column(Modifier.constrainAs(group) {
                linkTo(parent.top, parent.bottom)
                linkTo(
                    icon.end,
                    radioButton.start,
                    startMargin = 20.dp,
                    endMargin = 20.dp,
                    bias = 0f
                )
                width = Dimension.fillToConstraints
            }) {
                Text(
                    text = titleText,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = descriptionText,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}

private data class Token(
    val text: String,
    val type: String
)

@Composable
private fun parseProtocol(text: String): AnnotatedString {
    val tokens: MutableList<Token> = ArrayList()
    var index = 0
    var ch: Char
    val cache = StringBuilder()
    var inTitleContext = false
    var inBoldContext = false
    while (index < text.length) {
        ch = text[index]
        when {
            ch == '<' && !inTitleContext -> {
                inTitleContext = true
                if (cache.isNotEmpty()) {
                    tokens.add(Token(cache.toString(), "text"))
                    cache.clear()
                }
            }

            ch == '>' && inTitleContext -> {
                inTitleContext = false
                if (cache.isNotEmpty()) {
                    tokens.add(Token(cache.toString(), "title"))
                    cache.clear()
                }
            }

            ch == '(' && !inBoldContext -> {
                inBoldContext = true
                if (cache.isNotEmpty()) {
                    tokens.add(Token(cache.toString(), "text"))
                    cache.clear()
                }
            }

            ch == ')' && inBoldContext -> {
                inBoldContext = false
                if (cache.isNotEmpty()) {
                    tokens.add(Token(cache.toString(), "bold"))
                    cache.clear()
                }
            }

            ch == '{' -> {
                cache.append("(")
            }

            ch == '}' -> {
                cache.append(")")
            }

            else -> {
                cache.append(ch)
            }
        }
        index++
    }
    if (index == text.length) {
        if (inTitleContext) {
            tokens.add(Token(cache.toString(), "title"))
            cache.clear()
        } else {
            tokens.add(Token(cache.toString(), "text"))
            cache.clear()
        }
        if (inBoldContext) {
            tokens.add(Token(cache.toString(), "bold"))
            cache.clear()
        } else {
            tokens.add(Token(cache.toString(), "text"))
            cache.clear()
        }
    }

    return buildAnnotatedString {
        for (token in tokens) {
            when (token.type) {
                "title" -> append(
                    AnnotatedString(
                        token.text,
                        SpanStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        ParagraphStyle()
                    )
                )

                "bold" -> append(
                    AnnotatedString(
                        token.text,
                        SpanStyle(fontWeight = FontWeight.Bold)
                    )
                )

                else -> {
                    append(
                        AnnotatedString(
                            token.text,
                            SpanStyle(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f))
                        )
                    )
                }
            }
        }
    }
}