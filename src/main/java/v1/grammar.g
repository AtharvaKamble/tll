program ::= {statement}

statement ::= "PRINT" (expr | string) nl
            | "VARIABLE" ident "=" (expr | number | string) nl
            | "IF" comparison nl {statement} nl

comparison ::= expr (("==" | "!=" | ">=" | "<=" | ">" | "<") expr)

expr ::= term {("-" | "+") term}

// term ::=