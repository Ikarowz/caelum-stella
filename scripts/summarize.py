import csv, sys, datetime, pathlib

label = sys.argv[1]
alvos = {"CPFValidator", "CNPJValidator"}

jc = pathlib.Path("stella-core/target/site/jacoco/jacoco.csv")
lm = lc = bm = bc = 0
with jc.open(newline="", encoding="utf-8") as f:
    for r in csv.DictReader(f):
        if r["CLASS"] in alvos:
            lm += int(r["LINE_MISSED"]); lc += int(r["LINE_COVERED"])
            bm += int(r["BRANCH_MISSED"]); bc += int(r["BRANCH_COVERED"])
line_cov = 100.0 * lc / (lc + lm) if (lc + lm) else 0.0
branch_cov = 100.0 * bc / (bc + bm) if (bc + bm) else 0.0

pit = pathlib.Path("stella-core/target/pit-reports/mutations.csv")
status = {}
with pit.open(newline="", encoding="utf-8") as f:
    for row in csv.reader(f):
        status[row[5]] = status.get(row[5], 0) + 1
total = sum(status.values())
killed = status.get("KILLED", 0)
score = 100.0 * killed / total if total else 0.0

out = pathlib.Path("metrics/measurements.csv")
novo = not out.exists()
with out.open("a", newline="", encoding="utf-8") as f:
    w = csv.writer(f)
    if novo:
        w.writerow(["rotulo", "data", "cobertura_linhas_%", "cobertura_ramos_%",
                    "mutantes_total", "mortos", "sobreviventes", "sem_cobertura", "mutation_score_%"])
    w.writerow([label, datetime.date.today().isoformat(), f"{line_cov:.1f}", f"{branch_cov:.1f}",
                total, killed, status.get("SURVIVED", 0), status.get("NO_COVERAGE", 0), f"{score:.1f}"])
print(f"[{label}] linhas={line_cov:.1f}% ramos={branch_cov:.1f}% mutantes={total} mortos={killed} mutation_score={score:.1f}%")
