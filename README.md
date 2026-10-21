# Lab 1 – Installing Jenkins

📖 **Lab guide:** https://nouhakhyari-sys.github.io/jenkins-lab/

This repository is **your personal lab repository**. Your teacher can see it. You will use it for every Jenkins lab of the module.

## Your tasks

1. Follow the lab guide and install Jenkins **with Docker** (page 2.4 of the guide).
2. Complete the setup wizard and create **your own administrator account** (page 3).
3. Fill in [`REPORT.md`](REPORT.md): the commands you ran, your Jenkins version, and the answers to the questions.
4. Add your screenshots to the [`screenshots/`](screenshots/) folder:
   - `docker-ps.png`: the output of `docker ps` showing your `jenkins-local` container running
   - `dashboard.png`: your Jenkins dashboard, with the version visible in the footer
5. Commit and push your work before the deadline.

## How to submit

**Option 1: with Git (recommended, you will need it for the next labs)**

```bash
git clone <the URL of THIS repository>
cd <repository folder>
# edit REPORT.md, copy your screenshots into screenshots/
git add .
git commit -m "Lab 1: Jenkins installed"
git push
```

**Option 2: in the browser**
Open `REPORT.md` → pencil icon → edit → **Commit changes**.
For screenshots: open the `screenshots` folder → **Add file** → **Upload files**.

## Checklist before the deadline

- [ ] `REPORT.md` is complete (name, commands, version, answers)
- [ ] `screenshots/docker-ps.png` is uploaded
- [ ] `screenshots/dashboard.png` is uploaded, with the Jenkins version visible
- [ ] Everything is pushed (refresh this page on GitHub to check)
